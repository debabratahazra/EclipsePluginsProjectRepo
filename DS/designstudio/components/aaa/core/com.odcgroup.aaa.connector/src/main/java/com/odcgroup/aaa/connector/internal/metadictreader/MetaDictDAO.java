package com.odcgroup.aaa.connector.internal.metadictreader;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.openjpa.persistence.OpenJPAEntityManager;
import org.apache.openjpa.persistence.OpenJPAPersistence;

import com.odcgroup.aaa.connector.domainmodel.DictAttribute;
import com.odcgroup.aaa.connector.domainmodel.DictEntity;
import com.odcgroup.aaa.connector.domainmodel.DictPermValue;
import com.odcgroup.aaa.connector.internal.InvalidMetaDictException;
import com.sybase.jdbc3.tds.SybTimestamp;


/**
 * The Data Access Object Assembler for a MetaDictRepository.
 *
 * @author Michael Vorburger (MVO)
 */
public class MetaDictDAO {

    private static final String COL_SEPARATOR = "\t";
	protected EntityManager em;
	private static final Logger LOG = Logger.getLogger(MetaDictDAO.class);
    /**
     * DAO Constructor.
     *
     * @param em EntityManager to retrieve the T'A MetaDict from
     */
    public MetaDictDAO(EntityManager em) {
        if (em == null) {
            throw new IllegalArgumentException("em == null");
        }
        this.em = em;
    }

    public MetaDictRepository getMetaDict() {
        Set<DictEntity> entities = getEntities();
		return new MetaDictRepository(entities, getTAVersion());
    }

	public TAVersion getTAVersion() {
		Query query = em.createNativeQuery("select value_n from appl_param_vw where param_name='AAA_VERSION'", String.class);
		return new TAVersion((String)query.getSingleResult());
	}

	protected Set<DictEntity> getEntities() {
        List<DictAttribute> attributes = loadDictEntitiesAndAttributes();
        loadDictPermValues();
        Set<DictEntity> entities = createCorrectedEntities(attributes);
        return entities;
    }


    @SuppressWarnings("unchecked")
    private List<DictAttribute> loadDictEntitiesAndAttributes() {
        // Load all DictAttribute. Done like this because loading the DictEntity and hoping for a PARALLEL load of the
        // associated DictAttribute, through 2 instead of N queries, did not work)
        Query q = em.createQuery("SELECT a FROM DictAttribute a WHERE a.dictEntity.nature_e <> 4 AND a.dictEntity.xd_status_e IN (2,3) AND a.xd_status_e IN (2,3)");
        List<DictAttribute> attributes = q.getResultList();

        // Load all DictEntity into EM cache.
        // This allows us to keep DictAttribute.dictEntity & .referencedDictEntity as LAZY...
        // We don't actually keep the result of getResultList(), the goal is just to run the query.
        // Do not load entity with nature_e == 4 as they are UDE
        q = em.createQuery("SELECT e FROM DictEntity e WHERE e.nature_e <> 4 AND e.xd_status_e IN (2,3)");
        q.getResultList();

        for (DictAttribute attribute : attributes) {
            // .. force access to "reconnect" (re-wire) them here - without causing any SQL access through lazy loading!
            attribute.getDictEntity().getName();
            attribute.getReferencedDictEntity();
            // and same for the "self reference" (recursive) - the Attribute is already loaded, so no SQL for this
            attribute.getParentAttribute();
        }

        return attributes;
    }

    @SuppressWarnings("unchecked")
    private void loadDictPermValues() {
        Query q = em.createQuery("SELECT e FROM DictPermValue e WHERE e.attribute.dictEntity.nature_e <> 4 AND e.attribute.dictEntity.xd_status_e IN (2,3) AND e.xd_status_e IN (2,3) ORDER BY e.attribute, e.rank_n");
        List<DictPermValue> permValueEntites = q.getResultList();

        // The only problem left is that DictAttribute.dictPermValues (the "inverse") is not set
        // I couldn't figure out how to keep it JPA mapped yet "override" it (to prevent N lazy queries!),
        // so instead DictAttribute.dictPermValues is @Transient, and "fixed" manually
        for (DictPermValue dictPermValue : permValueEntites) {
            dictPermValue.getAttribute().getPermValues().add(dictPermValue);
        }

    }

    private Set<DictEntity> createCorrectedEntities(List<DictAttribute> attributes) {
        // Again the only problem left is that DictEntity.attributes (the "inverse") is not set
        // I couldn't figure out how to keep it JPA mapped yet "override" it (to prevent N lazy queries!),
        // so instead DictEntity.attributes is @Transient, and set manually
        Set<DictEntity> newEntitySet = new HashSet<DictEntity>();
        for (DictAttribute attribute : attributes) {
            DictEntity entity = attribute.getDictEntity();
            entity.addAttribute(attribute);

            newEntitySet.add(entity);
        }
        return newEntitySet;

        // BEWARE if there is ever any JPA detachment (explicit via detachAll API; or presumably same if serialized for remoting)
        // because detachAll() makes a copy of the managed fields, but looses JPA transient fields, this would have to be done AFTER detachment...
    }


	private StringBuffer readDataFromCheckMetaDict(Connection c) throws InvalidMetaDictException {
		CallableStatement st = null;
		try {
			st = c.prepareCall("{call check_meta_dict}");
			ResultSet res = st.executeQuery();
 			if (res != null) {
				return processResultSet(st, res);
			}
			return null;
		} catch (Exception e) {
			throw new InvalidMetaDictException("An error occured during meta dict validation.\n"+ e.getMessage());
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					LOG.error("Error closing statement", e);
				}
			}
		}
	}
	
	/**
	 * @param st
	 * @param res
	 * @return null if no errors have been found
	 * @throws SQLException
	 */
	private StringBuffer processResultSet(CallableStatement st, ResultSet res)	throws SQLException {
		
		final String OK_STATUS = "0";  // no errors
		
		Object lastDataRead = null; 
		// "0" means no errors, "1" means error
		StringBuffer buffer = new StringBuffer();

		// process first result-set
		processWarnings(st, buffer);
		int nbColumns =  processHeader(res, buffer);
		lastDataRead = processRows(res, nbColumns, buffer);
		
		boolean hasMoreResultSet = false;
		int updateCount = -1;
		
		// iterate over additional resultsets
		do {
			hasMoreResultSet = st.getMoreResults();
			if (hasMoreResultSet) {
				res = st.getResultSet();
				processWarnings(st, buffer);
				nbColumns = processHeader(res, buffer);
	    		lastDataRead = processRows(res, nbColumns, buffer);
			} else {
				updateCount = st.getUpdateCount();
			}
		} while (hasMoreResultSet || updateCount != -1);
		
		/* check if error(s) has been found
		 * the last data read contains a status equals to "0" or "1"
		 * a status of "0" means no error in the metadict.
		 */
		String status = lastDataRead.toString();
		if (status == null) status = "";
		
		if (OK_STATUS.equals(status.trim())) {
			// no errors, discard the buffer
			buffer = null;
		}
		
		return buffer;
	}
	
	private Object processRows(ResultSet res, int nbColumns, StringBuffer buffer) throws SQLException {
		Object lastDataRead = null;
		while (res.next()) {
			for (int i = 1; i <= nbColumns; i++) {
				Object data = res.getObject(i);
				buffer.append(data + COL_SEPARATOR);
				if (i == 1) {
					lastDataRead = data;
				}
				
			}
			buffer.append("\n");
		}
		return lastDataRead;
	}
	
	private void processWarnings(CallableStatement st, StringBuffer buffer) throws SQLException {
        SQLWarning warn = st.getWarnings();
        st.clearWarnings();
        if (warn != null) {
        	buffer.append(warn.getMessage());
        	buffer.append("\n");
        }
	}

	private int  processHeader(ResultSet res, StringBuffer buffer) throws SQLException {
		ResultSetMetaData  metadata = res.getMetaData();
		int nbColumns = res.getMetaData().getColumnCount();
		for (int i = 1; i <= nbColumns; i++) {
			buffer.append(metadata.getColumnName(i));
			buffer.append(COL_SEPARATOR);
		}
		buffer.append("\n----------------------------------------------------------------\n");
		return nbColumns;
	}

    //DS-4187 Calling an SP to validate metadict
    public StringBuffer validateMetaDict() throws InvalidMetaDictException {
    	OpenJPAEntityManager kem = OpenJPAPersistence.cast(em);
    	Connection conn = (Connection) kem.getConnection();
    	return readDataFromCheckMetaDict(conn);
    }

    /**
     * Simply try a database connection, if createNativeQuery throws an error, database is down
     * @see http://rd.oams.com/browse/DS-4475
     */
    public void validateConnection(){
    	Query q = em.createNativeQuery("SELECT 1");
    	q.getResultList();
    	
    }
    /**
     * Get the Timestamp.
     * @return
     */
    public String getTimeStamp(){
	String timeStamp =StringUtils.EMPTY;
	Query query = em.createNativeQuery("select last_modif_d from table_modif_stat_vw where entity_dict_id=1101");
	SybTimestamp sybTimestamp = (SybTimestamp)query.getSingleResult();
	if(sybTimestamp !=null){
	    timeStamp =sybTimestamp.toString();
	}
	return timeStamp ;
    }
}
