package com.zealcore.se.iw;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.ui.IMemento;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.zealcore.se.core.services.IMemento2;
import com.zealcore.se.iw.types.internal.FieldTypeFactory;
import com.zealcore.se.iw.types.internal.IFieldType;
import com.zealcore.se.iw.types.internal.ImportBehaviour;
import com.zealcore.se.iw.types.internal.StringType;


public class GenericTextImportDataTest {

    private static final String TEST_FILE_NAME = "testFile";

    private static final String SUBJECT_NAME = "Subject";

    private static final String FIELD_NAME_1 = "field1";

    private static final String DELIMITER_1 = "d1";

    private static final String FIELD_NAME_2 = "field2";

    private static final String DELIMITER_2 = "d2";

    private GenericTextImportData subject;

    private FieldDescriptor field1;

    private FieldDescriptor field2;

    private IMocksControl mocksControl;

    private final File testFile = new File(
            GenericTextImportDataTest.TEST_FILE_NAME);

    private final String filter1 = "-*";

    private final String filter2 = "#*";

    @Before
    public void setUp() throws Exception {
        // Create Fields
        this.field1 = createField(GenericTextImportDataTest.FIELD_NAME_1,
                GenericTextImportDataTest.DELIMITER_1);
        this.field2 = createField(GenericTextImportDataTest.FIELD_NAME_2,
                GenericTextImportDataTest.DELIMITER_2);
        final ArrayList<FieldDescriptor> fields = new ArrayList<FieldDescriptor>();
        fields.add(this.field1);
        fields.add(this.field2);

        final Set<String> filters = new HashSet<String>();
        filters.add(this.filter1);
        filters.add(this.filter2);

        // Create subject
        this.subject = new GenericTextImportData(
                GenericTextImportDataTest.SUBJECT_NAME, fields, filters,
                new ImportBehaviour(false, 0), this.testFile);

        // Create mocksControl
        this.mocksControl = EasyMock.createControl();
    }

    /**
     * 
     */
    private FieldDescriptor createField(final String name,
            final String delimeter) {
        final FieldDescriptor field = new FieldDescriptor();
        field.setDelimiter(delimeter);
        field.setName(name);
        final IFieldType fieldType = FieldTypeFactory.getInstance()
                .getFieldType(StringType.ID);
        field.setType(fieldType);
        return field;
    }

    @After
    public void tearDown() throws Exception {}

    @Test
    public void testPersistency() throws Exception {

        final IMemento2 subjectMemento = this.mocksControl
                .createMock(IMemento2.class);
        final IMemento2 fieldsMemento = this.mocksControl
                .createMock(IMemento2.class);
        final IMemento2 fieldMemento = this.mocksControl
                .createMock(IMemento2.class);
        final IMemento2 filtersMemento = this.mocksControl
                .createMock(IMemento2.class);
        final IMemento2 filterMemento = this.mocksControl
                .createMock(IMemento2.class);

        subjectMemento.putString(GenericTextImportData.KEY_DEFAULT_EVENT_NAME,
                this.subject.getDefaultEventName());
        subjectMemento.putString(GenericTextImportData.KEY_FILEID,
                GenericTextImportDataTest.TEST_FILE_NAME);
        subjectMemento.putInteger(GenericTextImportData.KEY_NOF_HEADERLINES, 0);
        subjectMemento
                .putInteger(GenericTextImportData.KEY_SKIP_EMPTY_LINES, 0);
        EasyMock
                .expect(
                        subjectMemento
                                .createChild(GenericTextImportData.KEY_FIELD_DESCRIPTORS))
                .andReturn(fieldsMemento);

        for (final FieldDescriptor descriptor : this.subject.getDescriptors()) {
            EasyMock
                    .expect(
                            fieldsMemento
                                    .createChild(GenericTextImportData.KEY_FIELD_DESCRIPTOR))
                    .andReturn(fieldMemento);
            fieldMemento.putString(FieldDescriptor.KEY_NAME, descriptor
                    .getName());
            fieldMemento.putString(FieldDescriptor.KEY_DELIMITER, descriptor
                    .getDelimiter());
            fieldMemento.putString(FieldDescriptor.KEY_TYPE, StringType.ID);
        }

        // save filters
        EasyMock.expect(
                subjectMemento
                        .createChild(GenericTextImportData.KEY_FILTERS_ROOT))
                .andReturn(filtersMemento);
        for (final String filter : this.subject.getFilters()) {
            EasyMock.expect(
                    filtersMemento
                            .createChild(GenericTextImportData.KEY_FILTER))
                    .andReturn(filterMemento);
            filterMemento.putString(GenericTextImportData.KEY_FILTER_TEXT,
                    filter);
        }

        this.mocksControl.replay();
        this.subject.save(subjectMemento);
        this.mocksControl.verify();

        this.mocksControl.reset();

        EasyMock
                .expect(
                        subjectMemento
                                .getString(GenericTextImportData.KEY_DEFAULT_EVENT_NAME))
                .andReturn(this.subject.getDefaultEventName());
        EasyMock.expect(
                subjectMemento.getString(GenericTextImportData.KEY_FILEID))
                .andReturn(GenericTextImportDataTest.TEST_FILE_NAME);
        EasyMock.expect(
                subjectMemento
                        .getInteger(GenericTextImportData.KEY_NOF_HEADERLINES))
                .andReturn(0);
        EasyMock
                .expect(
                        subjectMemento
                                .getInteger(GenericTextImportData.KEY_SKIP_EMPTY_LINES))
                .andReturn(0);

        EasyMock.expect(
                subjectMemento
                        .getChild(GenericTextImportData.KEY_FIELD_DESCRIPTORS))
                .andReturn(fieldsMemento);

        final ArrayList<IMemento2> fieldMementos = new ArrayList<IMemento2>();
        for (int i = 0; i < this.subject.getDescriptors().size(); ++i) {
            fieldMementos.add(fieldMemento);
        }

        EasyMock
                .expect(
                        fieldsMemento
                                .getChildren(GenericTextImportData.KEY_FIELD_DESCRIPTOR))
                .andReturn(fieldMementos.toArray(new IMemento[] {}));

        for (final FieldDescriptor descriptor : this.subject.getDescriptors()) {
            EasyMock.expect(fieldMemento.getString(FieldDescriptor.KEY_NAME))
                    .andReturn(descriptor.getName());
            EasyMock.expect(
                    fieldMemento.getString(FieldDescriptor.KEY_DELIMITER))
                    .andReturn(descriptor.getDelimiter());
            EasyMock.expect(fieldMemento.getString(FieldDescriptor.KEY_TYPE))
                    .andReturn(StringType.ID);
        }

        // restore filters
        EasyMock
                .expect(
                        subjectMemento
                                .getChild(GenericTextImportData.KEY_FILTERS_ROOT))
                .andReturn(filtersMemento);
        final ArrayList<IMemento2> filterMementos = new ArrayList<IMemento2>();
        for (int i = 0; i < this.subject.getFilters().size(); ++i) {
            filterMementos.add(filterMemento);
        }

        EasyMock.expect(
                filtersMemento.getChildren(GenericTextImportData.KEY_FILTER))
                .andReturn(filterMementos.toArray(new IMemento[] {}));
        for (final String filter : this.subject.getFilters()) {
            EasyMock.expect(
                    filterMemento
                            .getString(GenericTextImportData.KEY_FILTER_TEXT))
                    .andReturn(filter);
        }

        // dont forget to update the equals method on the subject when adding or
        // removing persistency
        // code on the subject (adding/removing persistant properties)

        this.mocksControl.replay();
        final GenericTextImportData restoredSubject = new GenericTextImportData(
                subjectMemento);
        this.mocksControl.verify();
        junit.framework.Assert.assertEquals(this.subject, restoredSubject);

    }

    @Test
    public void testCopy() {

        final GenericTextImportData data = new GenericTextImportData();

        final FieldDescriptor fieldOne = TestUtil.newDummyField("Field1", " ");

        final List<FieldDescriptor> fields = new ArrayList<FieldDescriptor>();
        fields.add(fieldOne);
        final List<String> ignore = new ArrayList<String>();
        ignore.add("IgnoreOne");
        ignore.add("IgnoreTwo");

        data.setDefaultEventName("Foo");
        data.setDescriptors(fields);
        data.setFileId("fooFieldId");
        data.setFilters(ignore);
        data.setNoOfHeaderLines(2);
        data.setSkipEmptyLines(true);

        final GenericTextImportData copy = data.copy();

        Assert.assertEquals(data.getDefaultEventName(), copy
                .getDefaultEventName());
        Assert.assertEquals(data.getFileId(), copy.getFileId());
        Assert.assertEquals(data.getSkipEmptyLines(), copy.getSkipEmptyLines());

        checkCopiedCollection(copy.getDescriptors(), data.getDescriptors());
        checkCopiedCollection(copy.getFilters(), data.getFilters());

    }

    private void checkCopiedCollection(final Collection<?> copy,
            final Collection<?> orig) {
        Assert.assertNotSame(copy, orig);
        for (final Object o : orig) {
            Assert.assertTrue(copy.contains(o));
        }
        final int size = copy.size();
        orig.clear();

        Assert.assertEquals(size, copy.size());
    }
}
