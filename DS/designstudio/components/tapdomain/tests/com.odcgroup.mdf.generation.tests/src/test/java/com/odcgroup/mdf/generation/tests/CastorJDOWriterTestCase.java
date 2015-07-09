package com.odcgroup.mdf.generation.tests;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collections;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.mdf.build.FileOutputStreamFactory;
import com.odcgroup.mdf.generation.legacy.castor.CastorJDOMapWriter;
import com.odcgroup.mdf.metamodel.MdfDomain;


/**
 * @version 1.0
 * @author <a href="mailto:dnemeshazy@odyssey-group.com">David Nemeshazy</a>
 */
public class CastorJDOWriterTestCase extends MdfGenerationTestCase {
	
	private static final String CASTOR_JDO_WRITER_TEST_FILE = "CastorJDOWriterTestCase";
	private static final String MML_INSTANCE = "torque-test.domain";

    @After
   public void tearDown() throws CoreException {
       super.tearDown();
       //-- Finally delete the folder
       delete(new File(CASTOR_JDO_WRITER_TEST_FILE));
   }

	@Test
    public void testCastorJDOMap() throws Exception {
    	
    	copyResourceInModelsProject("domain/mdf-generation-tests/" + MML_INSTANCE);

    	MdfDomain model = (MdfDomain) getOfsProject().getOfsModelResource(URI.createURI("resource:///mdf-generation-tests/" + MML_INSTANCE)).getEMFModel().get(0);
    	
        File root = new File(CASTOR_JDO_WRITER_TEST_FILE);
        FileOutputStreamFactory factory = new FileOutputStreamFactory(root);

        CastorJDOMapWriter wr = new CastorJDOMapWriter();
        wr.write(model, Collections.<MdfDomain>emptyList(), factory);

        // check generated

        File f = new File(root, "META-INF\\mappings\\torque-test.dbmap");
        Assert.assertTrue(f.exists());

        byte[] buffer = new byte[1024];
        byte[] result = new byte[0];
        BufferedInputStream bis = null;
        try {
	        bis = new BufferedInputStream(new FileInputStream(f));
	        int l = -1;
	        while ((l=bis.read(buffer))!=-1) {
	        	byte[] temp = new byte[result.length+l];
				System.arraycopy(result, 0, temp, 0, result.length);
	        	System.arraycopy(buffer, 0, temp, result.length, l);
	        	result = temp;
	        }
        } finally {
        	IOUtils.closeQuietly(bis);
        }

        String xml = new String(result);
        Assert.assertTrue(contains(xml, "<class name=\"tests.mdf.torque.bean.TorqueDataBean\" identity=\"id\" key-generator=\"OTF\">"));

        Assert.assertTrue(contains(xml, "<map-to table=\"torque_data\"/>"));
        Assert.assertTrue(contains(xml, "<field name=\"id\" type=\"long\">"));
        Assert.assertTrue(contains(xml, "<sql name=\"id\" type=\"numeric\"/>"));
        Assert.assertTrue(contains(xml, "<field name=\"field1\" type=\"string\">"));
        Assert.assertTrue(contains(xml, "<sql name=\"field_1\" type=\"varchar\"/>"));
		Assert.assertTrue(contains(xml, "<field name=\"field2\" type=\"integer\">"));
		Assert.assertTrue(contains(xml, "<sql name=\"field_2\" type=\"integer\"/>"));
		Assert.assertTrue(contains(xml, "<field name=\"field3\" type=\"boolean\">"));
		Assert.assertTrue(contains(xml, "<sql name=\"field_3\" type=\"bit\"/>"));
		Assert.assertTrue(contains(xml, "<field name=\"field4\" type=\"char\">"));
		Assert.assertTrue(contains(xml, "<field name=\"field5\" type=\"date\">"));
		Assert.assertTrue(contains(xml, "<sql name=\"field_5\" type=\"timestamp\" dirty=\"ignore\"/>"));
		Assert.assertTrue(contains(xml, "<field name=\"field6\" type=\"string\" handler=\"com.odcgroup.otf.castor.fieldHandler.URIFieldHandler\">"));
		Assert.assertTrue(contains(xml, "<sql name=\"field_6\" type=\"varchar\"/>"));
		Assert.assertTrue(contains(xml, "<field name=\"field8\" type=\"byte\">"));
		Assert.assertTrue(contains(xml, "<sql name=\"field_8\" type=\"tinyint\"/>"));
		Assert.assertTrue(contains(xml, "<field name=\"field9\" type=\"double\">"));
		Assert.assertTrue(contains(xml, "<sql name=\"field_9\" type=\"double\"/>"));
		Assert.assertTrue(contains(xml, "<field name=\"field10\" type=\"float\">"));
		Assert.assertTrue(contains(xml, "<sql name=\"field_10\" type=\"numeric\"/>"));
		Assert.assertTrue(contains(xml, "<field name=\"field11\" type=\"short\">"));
		Assert.assertTrue(contains(xml, "<sql name=\"field_11\" type=\"smallint\"/>"));
		Assert.assertTrue(contains(xml, "<field name=\"field12\" type=\"string\">"));
		Assert.assertTrue(contains(xml, "<sql name=\"field_12\" type=\"varchar\"/>"));
		Assert.assertTrue(contains(xml, "<field name=\"field13Ref\" type=\"com.odcgroup.mdf.core.ObjectId\" handler=\"com.odcgroup.mdf.utils.castor.fieldhandler.ObjectIdFieldHandler\">"));
		Assert.assertTrue(contains(xml, "<sql name=\"field_13\" type=\"varchar\"/>"));
		Assert.assertTrue(contains(xml, "<field name=\"field14Ref\" type=\"com.odcgroup.mdf.core.ObjectId\" handler=\"com.odcgroup.mdf.utils.castor.fieldhandler.ObjectIdFieldHandler\">"));
		Assert.assertTrue(contains(xml, "<sql name=\"field_14\" type=\"varchar\"/>"));
		Assert.assertTrue(contains(xml, "<field name=\"field17Ref\" type=\"com.odcgroup.mdf.core.ObjectId\" handler=\"com.odcgroup.mdf.utils.castor.fieldhandler.ObjectIdFieldHandler\">"));
		Assert.assertTrue(contains(xml, "<sql name=\"field_17\" type=\"varchar\"/>"));
		Assert.assertTrue(contains(xml, "<field name=\"field18Ref\" type=\"com.odcgroup.mdf.core.ObjectId\" handler=\"com.odcgroup.mdf.utils.castor.fieldhandler.ObjectIdFieldHandler\">"));
		Assert.assertTrue(contains(xml, "<sql name=\"field_18\" type=\"varchar\"/>"));
		Assert.assertTrue(contains(xml, "<field name=\"field19Ref\" type=\"com.odcgroup.mdf.core.ObjectId\" handler=\"com.odcgroup.mdf.utils.castor.fieldhandler.ObjectIdFieldHandler\">"));
		Assert.assertTrue(contains(xml, "<sql name=\"field_19\" type=\"varchar\"/>"));
		Assert.assertTrue(contains(xml, "<field name=\"field20Ref\" type=\"com.odcgroup.mdf.core.ObjectId\" handler=\"com.odcgroup.mdf.utils.castor.fieldhandler.ObjectIdFieldHandler\">"));
		Assert.assertTrue(contains(xml, "<sql name=\"field_20\" type=\"varchar\"/>"));

    }

    /**
     * @param src
     * @param check
     * @return
     */
    private boolean contains(String src, String check) {
    	return src.indexOf(check) != -1;
    }

    /**
     *
     * @param file
     */
    protected void delete(File file) {
        if (file.isDirectory()) {
            File[] children = file.listFiles();

            for (int i = 0; i < children.length; i++) {
                delete(children[i]);
            }
        }

        file.delete();
    }

}
