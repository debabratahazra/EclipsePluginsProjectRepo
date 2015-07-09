*-----------------------------------------------------------------------------
* <Rating>-84</Rating>
*-----------------------------------------------------------------------------
*----------------------------------------------------------------------------------------

    SUBROUTINE E.MB.BUILD.CREDIT.INT.CONDS(RET.ARR)

*----------------------------------------------------------------------------------------
* DESCRIPTION :
* -----------
* This routine is attached to the NOFILE enquiry CREDIT.INTEREST.CONDITIONS.
* The purpose of this enuqiry is to display the list of conditions set in the application ACCOUNT.CREDIT.INT
* If no conditions are available in ACCOUNT.CREDIT.INT application then it will display the list of conditions that are set in
* GROUP.CREDIT.INT application.
*-----------------------------------------------------------------------------------------
* REVESION HISTORY :
* ----------------
*
* VERSION : 1.0         DATE : 16 JUL 09    SAR : SAR-2009-01-14-0003
*                                           CD  : EN_10004268
*
* 23/07/10 - Task 66080
*            Change the reads to Customer to use the Customer
*            Service api calls
* * 12/11/10 - Task - 107259
*            Replace the enterprise(customer service api)code where it reads MNEMONIC.CUSTOMER file.
*-------------------------------------------------------------------------------------------


    $INSERT I_COMMON
    $INSERT I_EQUATE
    $INSERT I_ENQUIRY.COMMON
    $INSERT I_F.ACCOUNT.CREDIT.INT
    $INSERT I_F.GROUP.CREDIT.INT
    $INSERT I_F.CUSTOMER.ACCOUNT
    $INSERT I_F.MNEMONIC.ACCOUNT
    $INSERT I_F.ACCOUNT
    $INSERT I_DAS.COMMON
    $INSERT I_DAS.ACCOUNT.CREDIT.INT
    $INSERT I_DAS.GROUP.CREDIT.INT
    $INSERT I_CustomerService_Key    

    GOSUB INITIALISE
    GOSUB LOCATE.FIELDS
    GOSUB READ.ADI

    RETURN

*---------
INITIALISE:
*---------

    FN.ACCT.CREDIT.INT    = "F.ACCOUNT.CREDIT.INT"
    F.ACCT.CREDIT.INT     = ""

    FN.GRP.CREDIT.INT     = "F.GROUP.CREDIT.INT"
    F.GRP.CREDIT.INT      = ""

    FN.CUST.ACCT          = "F.CUSTOMER.ACCOUNT"
    F.CUST.ACCT           = ""

    FN.MNE.ACCT           = "F.MNEMONIC.ACCOUNT"
    F.MNE.ACCT            = ""

    FN.ACCOUNT            = "F.ACCOUNT"
    F.ACCOUNT             = ""


    CALL OPF(FN.ACCT.CREDIT.INT, F.ACCT.CREDIT.INT)
    CALL OPF(FN.GRP.CREDIT.INT, F.GRP.CREDIT.INT)
    CALL OPF(FN.CUST.ACCT, F.CUST.ACCT)
    CALL OPF(FN.MNE.ACCT, F.MNE.ACCT)
    CALL OPF(FN.ACCOUNT, F.ACCOUNT)
    CALL OPF(FN.CUST.ACCT, F.CUST.ACCT)


    RETURN


*------------
LOCATE.FIELDS:
*------------

*------------------------------------------------------------------------------------------------------------------------
*
* 1.
*
* This part of the routine is used to locate the CUSTOMER and ACCOUNT numbers that are input in the
* enquiry selection criteria. If CUSTOMER number is given in the selection then the ACCOUNT numbers of that customer
* are updated in the variable SEL.ACCT by reading the CUSTOMER.ACCOUNT application. If CUSTOMER mnemonic is given in the
* enquiry selection then the corresponding CUSTOMER number is updated in the variable SEL.CUST by reading the
* CUSTOMER.MNEMONIC application
*
* 2.
*
* If ACCOUNT number is given in the selction then it is used else if the ACCOUNT mnemonic is given in the selection
* then the corresponding ACCOUNT number is updated in the variable SEL.ACCT by reading the ACCOUNT.MNEMONIC application
*
*-------------------------------------------------------------------------------------------------------------------------


    LOCATE "CUSTOMER" IN D.FIELDS SETTING CUS.POS THEN

        Y.CUST = D.RANGE.AND.VALUE<CUS.POS>
        mnemonic = Y.CUST
        customerKey = ''
        ETEXT = ''
        CALL CustomerService.getCustomerForMnemonic(mnemonic, customerKey)
        
        IF NOT(ETEXT) THEN

            SEL.CUST = customerKey<Key.customerID>

            CALL F.READ(FN.CUST.ACCT, SEL.CUST, R.CUST.ACCT, F.CUST.ACCT, ERR.CUST.ACCT)

            SEL.ACCT = R.CUST.ACCT

        END ELSE
            ETEXT = ''
            SEL.CUST = Y.CUST

            CALL F.READ(FN.CUST.ACCT, SEL.CUST, R.CUST.ACCT, F.CUST.ACCT, ERR.CUST.ACCT)

            SEL.ACCT = R.CUST.ACCT

        END

        CONVERT FM TO VM IN SEL.ACCT

    END


    LOCATE "ACCOUNT" IN D.FIELDS SETTING ACC.POS THEN

        Y.ACCT = D.RANGE.AND.VALUE<ACC.POS>

        SEL.ACCT = Y.ACCT

        CALL  F.READ(FN.MNE.ACCT, Y.ACCT, R.MNE.ACCT, F.MNE.ACCT, ERR.MNE.ACCT)

        IF NOT(ERR.MNE.ACCT) THEN

            SEL.ACCT = R.MNE.ACCT

        END ELSE

            SEL.ACCT = Y.ACCT

        END

    END

    RETURN


*-------
READ.ADI:
*-------

*--------------------------------------------------------------------------------------------------------------------------
*
* This part of the subroutine does the following:
* ----------------------------------------------
* 1. The account numbers for the customer are in the variable SEL.ACCT
*    For each account number the ACCOUNT application is read and  the variables CUS.ACCT.CURR, CUS.ACCT.GRP.CON are updated
*    with the CURRENCY and  CONDTION.GROUP details.
*
* 2. A DAS select is done on the application ACCOUNT.CREIT.INT with @ID like <<account>>... and extract the records with date
*    less than TODAY and sort the records in descending order of Date.
*
* 3. Then the values of the variables CR.TAX.KEY, CR.BAL.TYPE, CR.CAL.TYPE, CR.MIN.BAL, CR.INT.RATE, CR.MAR.OPE, CR.MAR.RATE,
*    CR.MAX.RATE, CR.LIM.AMT are updated and returned in the variable RET.ARR
*
* 4. If there are no records in the application ACCOUNT.CREDIT.INT then a DAS select is made on the application
*    GROUP.CREDIT.INT to select the records with @ID like <<CONDITION.GROUP>>:<<CURRENCY>>...  and extract the records with
*    date less than TODAY and sort the records in descending order of Date.
*
* 5. Then the values of the variables GRP.TAX.KEY, GRP.BAL.TYPE, GRP.CAL.TYPE, GRP.MIN.BAL, GRP.INT.RATE, GRP.MAR.OPE,
*    GRP.MAR.RATE, GRP.MAX.RATE, GRP.LIM.AMT   are updated and returned in the variable RET.ARR
*
*-------------------------------------------------------------------------------------------------------------------------

    ACCT.CNT = DCOUNT(SEL.ACCT, @VM)

    LOOP

    WHILE ACCT.CNT GE '1'

        REMOVE CUS.ACCT FROM SEL.ACCT SETTING CUS.ACCT.POS

        CALL F.READ(FN.ACCOUNT, CUS.ACCT, R.ACCOUNT, F.ACCOUNT, ERR.ACCOUNT)

        CUS.ACCT.CURR = R.ACCOUNT<AC.CURRENCY>

        CUS.ACCT.GRP.CON = R.ACCOUNT<AC.CONDITION.GROUP>


        TABLE.NAME     = "ACCOUNT.CREDIT.INT"
        DAS.LIST       = dasAccountCreditIntIdByDateDsnd
        ARGUMENTS      = CUS.ACCT:'...'
        TABLE.SUFFIX   = ''

        CALL DAS(TABLE.NAME, DAS.LIST, ARGUMENTS, TABLE.SUFFIX)

        IF DAS.LIST NE '' THEN

            LOOP

                REMOVE ACC.CR.ID FROM DAS.LIST SETTING ACC.CR.ID.POS

            WHILE ACC.CR.ID:ACC.CR.ID.POS

                CALL F.READ(FN.ACCT.CREDIT.INT, ACC.CR.ID, R.ACCT.CREDIT.INT, F.ACCT.CREDIT.INT, ERR.ACCT.CREDIT.INT)

                CR.TAX.KEY    = R.ACCT.CREDIT.INT<IC.ACI.TAX.KEY>
                CR.BAL.TYPE   = R.ACCT.CREDIT.INT<IC.ACI.CR.BALANCE.TYPE>
                CR.CAL.TYPE   = R.ACCT.CREDIT.INT<IC.ACI.CR.CALCUL.TYPE>
                CR.MIN.BAL    = R.ACCT.CREDIT.INT<IC.ACI.CR.MINIMUM.BAL>
                CR.BASIC.RATE = R.ACCT.CREDIT.INT<IC.ACI.CR.BASIC.RATE>
                CR.INT.RATE   = R.ACCT.CREDIT.INT<IC.ACI.CR.INT.RATE>
                CR.MAR.OPE    = R.ACCT.CREDIT.INT<IC.ACI.CR.MARGIN.OPER>
                CR.MAR.RATE   = R.ACCT.CREDIT.INT<IC.ACI.CR.MARGIN.RATE>
                CR.MAX.RATE   = R.ACCT.CREDIT.INT<IC.ACI.CR.MAX.RATE>
                CR.LIM.AMT    = R.ACCT.CREDIT.INT<IC.ACI.CR.LIMIT.AMT>

                RET.ARR<-1> = CUS.ACCT:"*":CUS.ACCT.CURR:"*":CR.TAX.KEY:"*":CR.BAL.TYPE:"*":CR.CAL.TYPE:"*":CR.MIN.BAL:"*":CR.INT.RATE:"*":CR.MAR.OPE:"*":CR.MAR.RATE:"*":CR.MAX.RATE:"*":CR.LIM.AMT:"*":CR.BASIC.RATE:"*":TABLE.NAME:"*":ACC.CR.ID

            REPEAT

        END ELSE

            GOSUB READ.GCI

        END

        ACCT.CNT = ACCT.CNT-1

    REPEAT

    RETURN


*-------
READ.GCI:
*-------

    Y.ARGUMENTS = CUS.ACCT.GRP.CON:CUS.ACCT.CURR:'...'

    TABLE.NAME   = "GROUP.CREDIT.INT"
    DAS.LIST     = dasGroupCreditIntIdLikeByDate
    ARGUMENTS    = Y.ARGUMENTS
    TABLE.SUFFIX = ''

    CALL DAS(TABLE.NAME, DAS.LIST, ARGUMENTS, TABLE.SUFFIX)

    IF DAS.LIST NE '' THEN

        REMOVE ACC.GRP.ID FROM DAS.LIST SETTING ACC.GRP.ID.POS

        CALL F.READ(FN.GRP.CREDIT.INT, ACC.GRP.ID, R.GRP.CREDIT.INT, F.GRP.CREDIT.INT, ERR.GRP.CREDIT.INT)

        GRP.TAX.KEY    = R.GRP.CREDIT.INT<IC.GCI.TAX.KEY>
        GRP.BAL.TYPE   = R.GRP.CREDIT.INT<IC.GCI.CR.BALANCE.TYPE>
        GRP.CAL.TYPE   = R.GRP.CREDIT.INT<IC.GCI.CR.CALCUL.TYPE>
        GRP.MIN.BAL    = R.GRP.CREDIT.INT<IC.GCI.CR.MINIMUM.BAL>
        GRP.BASIC.RATE = R.GRP.CREDIT.INT<IC.GCI.CR.BASIC.RATE>
        GRP.INT.RATE   = R.GRP.CREDIT.INT<IC.GCI.CR.INT.RATE>
        GRP.MAR.OPE    = R.GRP.CREDIT.INT<IC.GCI.CR.MARGIN.OPER>
        GRP.MAR.RATE   = R.GRP.CREDIT.INT<IC.GCI.CR.MARGIN.RATE>
        GRP.MAX.RATE   = R.GRP.CREDIT.INT<IC.GCI.CR.MAX.RATE>
        GRP.LIM.AMT    = R.GRP.CREDIT.INT<IC.GCI.CR.LIMIT.AMT>

        *        R.RECORD<4> = GRP.INT.RATE

        RET.ARR<-1> = CUS.ACCT:"*":CUS.ACCT.CURR:"*":GRP.TAX.KEY:"*":GRP.BAL.TYPE:"*":GRP.CAL.TYPE:"*":GRP.MIN.BAL:"*":GRP.INT.RATE:"*":GRP.MAR.OPE:"*":GRP.MAR.RATE:"*":GRP.MAX.RATE:"*":GRP.LIM.AMT:"*":GRP.BASIC.RATE:"*":TABLE.NAME:"*":ACC.GRP.ID

    END

    RETURN
