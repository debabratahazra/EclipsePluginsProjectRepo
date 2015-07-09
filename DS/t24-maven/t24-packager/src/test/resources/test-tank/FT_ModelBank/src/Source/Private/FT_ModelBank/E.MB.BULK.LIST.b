*-----------------------------------------------------------------------------
*-----------------------------------------------------------------------------
* <Rating>-17</Rating>
*-----------------------------------------------------------------------------
    SUBROUTINE E.MB.BULK.LIST(ENQ.DATA)
*-----------------------------------------------------------------------------
* This is a build routine which is used to get the FT Bulk Master
* Ids for the authorisation based on the mandate setup.
*
* Modification History:
*======================
* 20/07/10 - Task 61126
*            Change the reads to Customer to use the Customer
*            Service api calls
*
*------------------------------------------------------------------------------
    $INSERT I_COMMON
    $INSERT I_EQUATE
    $INSERT I_ENQUIRY.COMMON
    $INSERT I_EB.EXTERNAL.COMMON
    $INSERT I_F.FUNDS.TRANSFER
    $INSERT I_F.FT.BULK.MASTER
    $INSERT I_F.EB.MANDATE
    $INSERT I_F.EB.SIGNATORY.GROUP
    $INSERT I_F.EB.MANDATE.PARAMETER
    $INSERT I_F.STANDARD.SELECTION
    $INSERT I_DAS.FT.BULK.MASTER
    $INSERT I_System
    $INSERT I_CustomerService_Mandates


    GOSUB INITIALISE
    GOSUB GET.MANDATE
    GOSUB GET.FT.IDS
    GOSUB GET.CONSTRAINT.FIELD
    GOSUB PROCESS
    RETURN

*-----------------------------------------------------------------------------
INITIALISE:

* Initialise the variables

    SMS.ACCOUNTS = System.getVariable('EXT.SMS.ACCOUNTS')
    IB.USER = System.getVariable('EXT.CUSTOMER')
    SMS.CUSTOMERS = System.getVariable('EXT.SMS.CUSTOMERS')

    CONVERT SM TO ' ' IN SMS.ACCOUNTS

    FN.FT.BULK.MASTER.NAU = 'F.FT.BULK.MASTER$NAU'
    F.FT.BULK.MASTER.NAU = ''
    CALL OPF(FN.FT.BULK.MASTER.NAU,F.FT.BULK.MASTER.NAU)

    FN.EB.MANDATE = 'F.EB.MANDATE'
    F.EB.MANDATE = ''
    CALL OPF(FN.EB.MANDATE,F.EB.MANDATE)

    FN.EB.MANDATE.PARAM = 'F.EB.MANDATE.PARAMETER'
    F.EB.MANDATE.PARAM = ''
    CALL OPF(FN.EB.MANDATE.PARAM,F.EB.MANDATE.PARAM)

    FN.EB.SIGNATORY.GROUP = 'F.EB.SIGNATORY.GROUP'
    F.EB.SIGNATORY.GROUP = ''
    CALL OPF(FN.EB.SIGNATORY.GROUP,F.EB.SIGNATORY.GROUP)

    FN.STANDARD.SELECTION = 'F.STANDARD.SELECTION'
    F.STANDARD.SELECTION = ''
    CALL OPF(FN.STANDARD.SELECTION,F.STANDARD.SELECTION)
    RETURN

*-----------------------------------------------------------------------------
GET.MANDATE:

* Get MANDATE.ID from customer


    MANDATE.ID = ''
    TOT.CUS = DCOUNT(SMS.CUSTOMERS,SM)
    FOR TOTAL.CUSTOMERS = 1 TO TOT.CUS
        IF MANDATE.ID EQ '' THEN
            CUSTOMER = FIELD(SMS.CUSTOMERS,SM,TOTAL.CUSTOMERS)
            customerKey = CUSTOMER
            customerMandates = ''
            CALL CustomerService.getMandates (customerKey, customerMandates)
            MANDATE.ID = customerMandates<Mandates.mandateRecordList>
            MANDATE.APPL = customerMandates<Mandates.mandateApplicationList>
        END
    NEXT TOTAL.CUSTOMERS
    RETURN

*-----------------------------------------------------------------------------
GET.FT.IDS:

    THE.ARGS<1> = 'IHLD'
    THE.ARGS<2> = 'IB.USER'
    THE.ARGS<3> = 'SMS.ACCOUNTS'
    DAS.TABLE.SUFFIX = '$NAU'

    SEL.LIST = dasFTBulkMasterWithRecStatusNeIHLD
    CALL DAS("FT.BULK.MASTER",SEL.LIST,THE.ARGS,DAS.TABLE.SUFFIX)
    RETURN

*-----------------------------------------------------------------------------
GET.CONSTRAINT.FIELD:

* Get field name for 'FT.BULK.MASTER' from EB.MANDATE.PARAMETER and get the field
* number for the same from STANDARD.SELECTION

    CALL CACHE.READ(FN.EB.MANDATE.PARAM,'FT.BULK.MASTER',R.EB.MAND.PARAM,MAND.ERR)
    CONSTRAINT.FIELD = R.EB.MAND.PARAM<EB.MAND.PAR.AMOUNT.FIELD>
    CALL GET.STANDARD.SELECTION.DETS('FT.BULK.MASTER',R.STANDARD.SELECTION)
    FT.FIELDS = R.STANDARD.SELECTION<SSL.SYS.FIELD.NAME>
    TOT.SS.FIELD.CNT = DCOUNT(FT.FIELDS,@VM)
    FOR NO.OF.FIELDS = 1 TO TOT.SS.FIELD.CNT
        IF CONSTRAINT.FIELD = FT.FIELDS<1,NO.OF.FIELDS> THEN
            FIELD.NO = R.STANDARD.SELECTION<SSL.SYS.FIELD.NO,NO.OF.FIELDS,1>
        END
    NEXT NO.OF.FIELDS
    RETURN

*-----------------------------------------------------------------------------
PROCESS:

* Get debit amount and check mandate conditions for each FT


    FT = ''
    LOOP
        REMOVE FT.ID FROM SEL.LIST SETTING FT.ID.POS
    WHILE FT.ID:FT.ID.POS
        CALL F.READ(FN.FT.BULK.MASTER.NAU,FT.ID,R.FT.REC,F.FT.BULK.MASTER.NAU,FT.ERR)
        DEBIT.AMOUNT = R.FT.REC<FIELD.NO>
        RECORD.STATUS = R.FT.REC<FT.BLK.MAS.RECORD.STATUS>
        IF RECORD.STATUS NE 'IHLD' THEN
            GOSUB CHECK.MANDATE.AMOUNT
        END
    REPEAT

    CONVERT @VM TO ' ' IN FT
    ENQ.DATA<2,1> = '@ID'
    ENQ.DATA<3,1> = 'EQ'
    ENQ.DATA<4,1> = FT
    RETURN

*-----------------------------------------------------------------------------
CHECK.MANDATE.AMOUNT:


* Check Mandate conditions for the given FT

    TEMP.FT = ''
    UP.TO.AMOUNT = ''
    BULK = "FT.BULK.MASTER"
    FIND BULK IN MANDATE.APPL SETTING POS,POS1 THEN
        MANDATE.VAL = MANDATE.ID<1,POS1>
    END
    CALL CACHE.READ(FN.EB.MANDATE,MANDATE.ID,R.MANDATE.REC,MANDATE.ERR)
    NO.OF.GROUPS = DCOUNT(R.MANDATE.REC<EB.MAND.UP.TO.AMOUNT>,VM)
    FOR GROUPS.CNT = 1 TO NO.OF.GROUPS
        UP.TO.AMOUNT = R.MANDATE.REC<EB.MAND.UP.TO.AMOUNT,GROUPS.CNT>
        IF (DEBIT.AMOUNT LE UP.TO.AMOUNT OR UP.TO.AMOUNT = '') AND TEMP.FT = '' THEN
            NO.OF.SIGNATORY.GROUP = DCOUNT(R.MANDATE.REC<EB.MAND.SIGNATORY.GROUP,GROUPS.CNT>,SM)
            FOR CURR.GROUP = 1 TO NO.OF.SIGNATORY.GROUP
                SIGNATORY.GROUP = R.MANDATE.REC<EB.MAND.SIGNATORY.GROUP,GROUPS.CNT,CURR.GROUP>
                CALL CACHE.READ(FN.EB.SIGNATORY.GROUP,SIGNATORY.GROUP,R.SIG.REC,SIG.ERR)
                SIGNATORY.CUSTOMERS = R.SIG.REC<EB.SIG.GRP.SIGNATORY.CUSTOMER>
                IF IB.USER MATCHES SIGNATORY.CUSTOMERS AND TEMP.FT = '' THEN
                    TEMP.FT<1,-1> = FT.ID
                    FT<1,-1> = FT.ID
                END
            NEXT NO.OF.SIGNATORY.GROUP
        END
    NEXT GROUPS.CNT
    RETURN
*-----------------------------------------------------------------------------
END
