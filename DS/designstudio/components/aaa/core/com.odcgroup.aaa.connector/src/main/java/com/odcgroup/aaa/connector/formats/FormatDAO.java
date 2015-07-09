package com.odcgroup.aaa.connector.formats;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.odcgroup.aaa.connector.domainmodel.FormatEntity;
import com.odcgroup.aaa.connector.domainmodel.TypeEntity;
import com.odcgroup.aaa.connector.internal.TANGIJException;
import com.odcgroup.aaa.connector.internal.util.FormatVO;
import com.odcgroup.aaa.connector.internal.util.FunctionVO;


/**
 * The Data Access Object Assembler for T'A Formats.
 * 
 * @author Michael Vorburger (MVO)
 */
public class FormatDAO {
    
	private EntityManager em;

    private static final String ALLTYPES_JPQL = "SELECT t FROM Type t WHERE t.attribute.dict_id = " + TypeEntity.FORMAT_TYPE_DICTID_STRING + " ORDER BY t.rank";
    private static final String ALLLANGS_JPQL = "SELECT l FROM DictLanguage l";
	private static final String ALLFORMATCODE_JPQL = "SELECT new com.odcgroup.aaa.connector.internal.util.FormatVO(f.code,f.denom,f.function.procName) FROM Format f";
    private static final String ALLFUNCTIONS_JPQL = "SELECT new com.odcgroup.aaa.connector.internal.util.FunctionVO(d.procName) From DictFunction d";

    /**
     * @see splitSelectedCodes
     */
    protected static final int MAX_SELECTED_CODES_IN_IN_CLAUSE = 1000; 
    
    /**
     * DAO Constructor.
     * 
     * @param em EntityManager to connect to T'A
     */
    public FormatDAO(EntityManager em) {
        this.em = em;
    }
    
    /**
     * Loads a Format from T'A.
     * 
     * @param formatCode Code of the T'A Format.  No wildcards.
     * @return Format
     */
    public FormatEntity getFormat(String formatCode) throws TANGIJException {
        if (formatCode == null) {
            throw new IllegalArgumentException("formatCode == null");
        }
        
        // Pre-load all languages (better than lazy loading for each denom, and probably more efficient, probably than JOINing on each?)
        em.createQuery(ALLLANGS_JPQL).getResultList();
        
        Query q = em.createQuery("SELECT o FROM Format o WHERE o.code = :code");
        q.setParameter("code", formatCode);
        
        try {
            return (FormatEntity) q.getSingleResult();
        } catch (NoResultException e) {
            throw new TANGIJException("No format with this code exist: " + formatCode);
        }
        
    }

    /**
     * Loads Formats from T'A.
     *
     * @param formatCodePattern Code of Format, possibly with pattern where '*' is wildcard.  Just '*' is allowed and means all formats. 
     * @return List of Formats
     */
    @SuppressWarnings("unchecked")
    public List<FormatEntity> getFormats(FormatCriteria criteria) {
        if (criteria == null) {
            throw new IllegalArgumentException("FormatCriteria criteria == null");
        }
        if (criteria.formatCodePattern == null && criteria.getSelectedCodes().size() == 0) {
            throw new IllegalArgumentException("FormatCriteria criteria.formatCodePattern == null AND criteria.selectedCode.size == 0");
        }
        if (criteria.formatCodePattern != null && criteria.getSelectedCodes().size() != 0) {
            throw new IllegalArgumentException("Incorrect FormatCriteria. Please choose between formatCodePattern and selected codes.");
        }
        
        // Pre-load all languages (better than lazy loading for each denom, and probably more efficient, probably than JOINing on each?)
        em.createQuery(ALLLANGS_JPQL).getResultList();
        
        if (criteria.getSelectedCodes().size() != 0) {
        	List<FormatEntity> result = new ArrayList<FormatEntity>();
        	for (Set<String> codesSlice : splitSelectedCodes(criteria.getSelectedCodes())) {
                Query q = em.createQuery("SELECT o FROM Format o WHERE o.code IN (:selectedCodes)");
                q.setParameter("selectedCodes", codesSlice);
                result.addAll(q.getResultList());
        	}
        	return result;
        } else if (criteria.formatCodePattern.contains("*") && criteria.type == null) {
            Query q = em.createQuery("SELECT o FROM Format o WHERE o.code LIKE :code");
            q.setParameter("code", criteria.formatCodePattern.replaceAll("\\*", "%"));
            return q.getResultList();
        } else if (criteria.type == null) {
            Query q = em.createQuery("SELECT o FROM Format o WHERE o.code = :code");
            q.setParameter("code", criteria.formatCodePattern);
            return q.getResultList();
        } else {
            Query q = em.createQuery("SELECT o FROM Format o WHERE o.code LIKE :code AND o.type = :type");
            q.setParameter("code", criteria.formatCodePattern.replaceAll("\\*", "%"));
            q.setParameter("type", criteria.type);
            return q.getResultList();
        }
    }

    /**
     * Sysbase 12.5 limits the number of condition in a query to 1024. When 
     * an in clause is used, each element in the clause count for a new condition.
     * Therefore, it is require for large set of selectedCode, to split it.
     * Note: with Sybase 15, the limit is gone, but appears to be very slow for
     * large in clause
	 * @param selectedCodes
	 * @return the selectedCodes split in slides
	 */
	private List<Set<String>> splitSelectedCodes(Set<String> selectedCodes) {
		List<Set<String>> result = new LinkedList<Set<String>>();
		if (selectedCodes.size() < MAX_SELECTED_CODES_IN_IN_CLAUSE) {
			result.add(selectedCodes);
		} else {
			Set<String> current = null;
			int count = 0;
			for (String code : selectedCodes) {
				if (count == 0) {
					current = new HashSet<String>();
					result.add(current);
				}
				current.add(code);
				count++;
				if (count == MAX_SELECTED_CODES_IN_IN_CLAUSE) {
					count = 0;
				}
			}
		}
		return result;
	}

	/**
     * Gets list of all available Format Types.
     * The return type is intentionally a List and not a Set or Collection, because it will be ordered by the "rank" of the Types.
     * This order should be respected when e.g. presented in a drop-down.
     * 
     * @return List<TypeEntity>
     */
    @SuppressWarnings("unchecked")
    public List<TypeEntity> getFormatTypes() {
        return em.createQuery(ALLTYPES_JPQL).getResultList();
    }

    /**
     * Gets a list of the codes of all Formats. 
     * @return List of String
     */
    @SuppressWarnings("unchecked")
    public List<FormatVO> getAllFormatCodes() {
        return (List<FormatVO>) em.createQuery(ALLFORMATCODE_JPQL).getResultList();
    }
    
    /**
     * Get a list of all function proc name
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<FunctionVO> getAllFunctions() {
        return (List<FunctionVO>) em.createQuery(ALLFUNCTIONS_JPQL).getResultList();
    }
}
