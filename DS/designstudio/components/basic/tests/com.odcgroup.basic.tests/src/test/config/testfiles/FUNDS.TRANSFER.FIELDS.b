*-----------------------------------------------------------------------------
* <Rating>6033</Rating>
*-----------------------------------------------------------------------------
    SUBROUTINE FUNDS.TRANSFER.FIELDS
********************************************************************************
*
* New Routine. All the Field definitions for FUNDS.TRANSFER done here.
*
********************************************************************************
*
* MODIFICATION HISTORY
*
* 14/10/08 - EN_10003874
*            DEFINE.PARMETERS para in FUNDS.TRANSFER moved into this new routine.
*
* 17/02/09 - BG_100022171
*            Making TREASURY.RATE, CUSTOMER.RATE & CUSTOMER.SPREAD as HOT.FIELDS
*
* 04/03/09 - CI_10061060
*            The length of cheque number is increased
*
* 19/03/09 - CI_10061425
*            BC.BANK.SORT.CODE with leading zero should be allowed, so changing the
*            T() to "A".
*
* 27/04/09 - EN_10004043
*            Swift 2009 Changes. Introduction of 202C/205C Messages
*            Ref: SAR-2008-12-19-0003
*            Introduced 4 new IN fields IN.C.ORD.BK, IN.C.INTMED.BK
*            IN.C.ACC.WIT.BK, IN.C.BK.T.BK.IN
*
* 12/11/09 - EN_10004437
*            Including the fields TFS.REFERENCE ,CHEQUE.DRAWN.
*            SAR.REF:SAR-2009-10-30-0001
*
* 02/02/10 - Task: 18196, Defect : 13934
*            FT Bulk Upload Changes.
*            Length of INWARD.PAY.TYPE increased since the FT.BULK.ITEM id is
*            increased.
*
* 17/02/10 - Task - 21647 , Defect - 20634.
*            POSITION.TYPE field opened for input to support multigaap when PL category
*            is involved.
*
* 03/05/10 - Task: 27861, Enhancement: 27278
*            Tag 50F and 51A support in MT103/102/202C
*
* 17/05/10 - Task - 27844, Defect - 26545
*            Adding options CRED, CRTS, SPAY, SPRI & SSTD for BK.OPERATION.CODE
*
* 07/09/10 - Defect 81192 / task 84359
*            Browser FT.COMMISSION.TYPE record with Zero Flat amount attached in the FT transaction,
*            after validation system unable to delete multivalue set instead nullify the multivalue set.
*            fix is T-array of ft.commission.type related fields modified to 'CLEAR' and handle the associate
*            multivalue R.NEW(FT.COMMISSION.FOR) field properly.
*
* 31/01/11 - Task 136882
*            When the FUNDS.TRANSFER @ID is displayed with the SORT-CRITERIA (BY-DSND) from the jshell prompt.
*            The result is excepted to be sorted from the Top–down approach. Since in the FUNDS.TRANSFER @ID
*            client has alphanumeric characters and they are ‘R’ justified, the o/p displayed is not in the
*            sorted order. Normally, 'R' right justified is used only for numeric values but here @ID contains
*            alphanumeric values it should be 'L' left justified.
*
* 31/01/11 - 120329
*            Banker's Draft Management.
*            Added new fields for internal cheques.
*
* 06/05/11 - Task 187151
*            Enhancement 187149
*            Added new field ORD.CUST.ACCT for supporting ordering customer account number in Tag 50 in
*            MT103 and MT102 messages.
*
* 20/09/11 - Task 274856
*            Fix done for drop down option for credit account number,debit account number,receiver bank and
*            receiver corresponding bank.
*
* 18/01/12 - 341426
*            '-' Option to be set for CUSTOMER.SPREAD field, to avoid validation of minus for system
*            defaulted values
*
* 15/02/12 - Task 356153
*            reverting mistakenly delivered code in Task 349528
*
* 29/05/12 - Task 413297
*            Input validation aganist CUST/BIC has been blocked only to when the field ORDERING.CUST is not MV.
*
* 08/06/12 - Task 419252
*            Made changes to allow input values which begins with minus for the field Payment details .
*
* 01/08/12 - Task 454874
* 			 Length of the PAYEE NAME field increased to 50
*
* 24/08/12 - Task 464771
*            ORD.CUST.ACCT is not allowing alpha numeric characters.
*
* 28/08/12 - Task 457353
*            IN2SW Validation.
*
* 03/06/12 - Enhancement 379826/SI 167927
*            Payments - Development for IBAN.
*
* 08/07/13 - Enhancement 671225 / Task 707307
*            Adding New fields to store BIC code for the IBAN.BEN account.
*
* 31/01/14 - Task 902396
*            Validation has included in FT to accept the CHARGE TYPE with FLAT AMOUNT as 0 whose decimals in currency is 0.
*
* 12/03/14 - TASK 938649
*            Changed the field attribute 'CUST.BIC' to 'A' in ORDERING.CUST, ORDERING.BANK, ACCT.WITH.BANK, BEN.CUSTOMER,
*            BEN.BANK, REC.CORR.BANK and INTERMED.BANK. Swift(SW) validation handled in FT.CROSSVAL routine
*
* 25/06/15 - Defect 1379851 / Task 1388813
*          - NOCOPY attribute set for the fields SEND.TO.PARTY and BK.TO.BK.OUT
*
***************************************************************************************************************
*
    $INSERT I_COMMON
    $INSERT I_EQUATE
    $INSERT I_F.COMPANY
    $INSERT I_F.ACCOUNT
    $INSERT I_F.FUNDS.TRANSFER
    $INSERT I_F.FT.COMMISSION.TYPE
    $INSERT I_F.FT.CHARGE.TYPE
    $INSERT I_F.FT.TXN.TYPE.CONDITION
    $INSERT I_F.CURRENCY
    $INSERT I_F.CURRENCY.MARKET
    $INSERT I_F.BENEFICIARY
    $INSERT I_GTS.COMMON
    $INSERT I_F.OFS.SOURCE
    $INSERT I_F.DEALER.DESK
    $INSERT I_F.CUSTOMER
    $INSERT I_F.FX.POS.TYPE
    $INSERT I_F.TAX
    $INSERT I_F.EB.ROUNDING.RULE
    $INSERT I_F.CHEQUE.TYPE
    $INSERT I_F.DEPT.ACCT.OFFICER
    $INSERT I_F.BC.SORT.CODE
    $INSERT I_F.CARD.ISSUE
    $INSERT I_F.SPF

*=======================================================================
    MAT F = "" ; MAT N = "" ; MAT T = "" ; ID.T = ""
    MAT CHECKFILE = "" ; MAT CONCATFILE = ""
    ID.CHECKFILE = "" ; ID.CONCATFILE = ""
*========================================================================
REM "DEFINE PARAMETERS - SEE 'I_RULES'-DESCRIPTION:
**
**  DO NOT RUN 'FILE.LAYOUT'. EDIT MANUALLY.
**  FILE.LAYOUT WILL NOT RUN DUE TO DUPLICATE FIELD NAMES
**
    ID.CONCATFILE = "AL"
*
* Use variable to set T params when GTS is active
*
    IF GTSACTIVE AND OFS$SOURCE.REC<OFS.SRC.SOURCE.TYPE> NE 'SESSION' THEN
        GTSNOINPUT = ''
        GTSNOCHANGE = ''
    END ELSE
        GTSNOINPUT = 'NOINPUT'
        GTSNOCHANGE = 'NOCHANGE'
    END
*
    ID.F = 'REF.NO'
    ID.N = '25'
    ID.T<1> = 'A' ; ID.T<4> = 'L##/#####/##############'    ;* GB0001898
*
    ID.CHECKFILE = ''
*
    F(1) = 'TRANSACTION.TYPE' ; N(1) = '4.2.C' ; T(1)<1> = 'A' ;T(1)<3> = 'NOCHANGE'
    CHECKFILE(1) = 'FT.TXN.TYPE.CONDITION': FM: FT6.DESCRIPTION: FM:'L.A'
    F(2) = 'DEBIT.ACCT.NO' ; N(2) = '16..C' ; T(2)<1> = '.ALLACCVAL' ; T(2)<3> = 'NOCHANGE'
    CHECKFILE(2) = 'ACCOUNT':FM:AC.SHORT.TITLE:FM:'L.A...Y'
    F(3) = 'IN.DEBIT.ACCT.NO' ; N(3) = '16' ; T(3)<1> = 'A' ; T(3)<3> = 'NOINPUT'
    F(4) = 'CURRENCY.MKT.DR' ; N(4) = '1..C' ; T(4)<1> = 'A' ; T(4)<3> = 'NOINPUT'
    CHECKFILE(4) = 'CURRENCY.MARKET':FM:EB.CMA.DESCRIPTION:FM:'L.A'
    F(5) = 'DEBIT.CURRENCY' ; N(5) = '3..C' ; T(5)<1> = 'CCY' ; T(5)<3> = 'NOCHANGE'
    CHECKFILE(5) = 'CURRENCY': FM: EB.CUR.CCY.NAME: FM:'L.A'
    F(6) = 'DEBIT.AMOUNT' ;N(6) = '18..C' ;T(6)<1> = 'AMT' ; T(6)<2> = '':VM:'5' ; T(6)<3> = 'NOCHANGE'
    F(7) = 'DEBIT.VALUE.DATE' ; N(7) = '11..C' ; T(7)<1> = 'D' ; T(7)<3> = 'NOCHANGE'
    F(8) = 'IN.DEBIT.VDATE' ; N(8) = '11' ; T(8)<1> = 'D' ; T(8)<3> = GTSNOINPUT
    F(9) = 'DEBIT.THEIR.REF' ; N(9) = '16' ; T(9)<1> = 'A' ; T(9)<3> = 'NOCHANGE'
    F(10) = 'CREDIT.THEIR.REF' ; N(10) = '27..C' ; T(10)<1> = 'A' ; T(10)<3> = 'NOCHANGE'
* Debit their ref & Credit their ref need not be validated for
* inward messages. So move the validations to FT.COMPLETE.XVALIDATION
    F(11) = 'CREDIT.ACCT.NO' ; N(11) = '16..C'; T(11)<1> = '.ALLACCVAL' ; T(11)<3> = 'NOCHANGE'
    CHECKFILE(11) = 'ACCOUNT':FM:AC.SHORT.TITLE:FM:'L.A...Y'
    F(12) = 'CURRENCY.MKT.CR' ; N(12) = '1..C' ; T(12)<1> = 'A' ; T(12)<3> = 'NOINPUT'
    CHECKFILE(12) = 'CURRENCY.MARKET':FM:EB.CMA.DESCRIPTION:FM:'L.A'
    F(13) = 'CREDIT.CURRENCY' ; N(13) = '3..C' ; T(13)<1> = 'CCY' ; T(13)<3> = 'NOCHANGE'
    CHECKFILE(13) = 'CURRENCY': FM: EB.CUR.CCY.NAME: FM:'L.A'
    F(14) = 'CREDIT.AMOUNT' ; N(14) = '18..C' ; T(14)<1> = 'AMT'
    T(14)<2> = '':VM:'13' ; T(14)<3> = 'NOCHANGE'
    F(15) = 'CREDIT.VALUE.DATE' ; N(15) = '11..C' ; T(15)<1> = 'D' ; T(15)<3> = 'NOCHANGE'
    F(16) = 'TREASURY.RATE' ; N(16) = '16..C' ; T(16)<1> = 'R' ; T(16)<3> = 'NOCHANGE' ; T(16)<9> = 'HOT.FIELD'
    F(17) = 'NEG.DEALER.REFNO' ; N(17) = '5..C' ;  T(17)<1> = '' ; T(17)<3> = 'NOCHANGE'
    F(18) = 'PROCESSING.DATE' ; N(18) = '11..C' ; T(18)<1> = 'D' ; T(18)<3> = 'NOCHANGE'
    F(19) = 'XX.ORDERING.CUST' ; N(19) = '35..C' ; T(19)<1> = 'A'; T(19)<2,1> = 'SW'; T(19)<3> = 'NOCHANGE'
    F(20) = 'XX.IN.ORDERING.CUS' ; N(20) = '35' ; T(20)<1> = 'A' ; T(20)<3> = GTSNOINPUT
    F(21) = 'XX.ORDERING.BANK' ; N(21) = '35..C' ; T(21)<1> = 'A' ; T(21)<2,1> = 'SW'
    T(21)<3> = 'NOCHANGE'
    F(22) = 'XX.IN.ORDERING.BK' ; N(22) = '35' ;  T(22)<1> = 'A' ; T(22)<3> = GTSNOINPUT
    F(23) = 'XX.ACCT.WITH.BANK' ; N(23) = '035..C' ; T(23)<1> = 'A' ; T(23)<2,1> = 'SW'
    T(23)<3> = 'NOCHANGE'
    F(24) = 'XX.ACCT.WITH.BK' ; N(24) = '35' ; T(24)<1> = 'A' ; T(24)<3> = GTSNOINPUT
    F(25) = 'BEN.ACCT.NO' ; N(25) = '034..C' ; T(25)<1> = 'S' ; T(25)<3> = 'NOCHANGE'
    F(26) = 'IN.BEN.ACCT.NO' ; N(26) = '34' ; T(26)<1> = 'A' ; T(26)<3> = GTSNOINPUT
    F(27) = 'XX.BEN.CUSTOMER' ; N(27) = '35..C' ; T(27)<1> = 'A' ; T(27)<2,1> = 'SW'
    T(27)<3> = 'NOCHANGE'
    F(28) = 'XX.IN.BEN.CUSTOMER' ; N(28) = '35' ; T(28)<1> = 'A' ; T(28)<3> = GTSNOINPUT
    F(29) = 'XX.BEN.BANK' ; N(29) = '35..C' ; T(29)<1> = 'A' ; T(29)<2,1> = 'SW'
    T(29)<3> = 'NOCHANGE'
    F(30) = 'XX.IN.BEN.BANK' ; N(30) = '35' ; T(30)<1> = 'A' ; T(30)<3> = GTSNOINPUT
    F(31) = 'CHEQUE.NUMBER' ; N(31) = '35..C' ; T(31)<1> = 'A' ; ;T(31)<3> = 'NOCHANGE'
    F(32) = 'XX.PAYMENT.DETAILS' ; N(32) = '35..C' ; T(32)<1> = 'S' ; T(32)<3> = 'NOCHANGE'
    F(33) = 'XX.IN.PAY.DETAILS' ; N(33) = '35' ; T(33)<1> = 'A' ; T(33)<3> = GTSNOINPUT
    F(34) = 'BC.BANK.SORT.CODE' ; N(34) = '20..C' ; T(34)<1> = 'A' ; T(34)<3> = 'NOCHANGE'
    CHECKFILE(34) = "BC.SORT.CODE":FM:EB.BSC.NAME:FM:'L.A.S..IGNORE.R'
    F(35) = 'RECEIVER.BANK' ; N(35) = '35..C' ; T(35)<1> = 'CUST.BIC' ; T(35)<2,1> = 'SW'
    T(35)<2,2> ='' ; T(35)<3> = 'NOCHANGE'
    CHECKFILE(35) = 'CUSTOMER':FM:EB.CUS.SHORT.NAME:FM:'L.A...Y'
    F(36) = 'XX.REC.CORR.BANK' ; N(36) = '35..C' ; T(36)<1> = 'A' ; T(36)<2,1> = 'SW'
    T(36)<3> = 'NOCHANGE'
    CHECKFILE(36) = 'CUSTOMER':FM:EB.CUS.SHORT.NAME:FM:'L.A...Y'
    F(37) = 'XX.INTERMED.BANK' ; N(37) = '35..C' ; T(37)<1> = 'A' ; T(37)<2,1> = 'SW'
    T(37)<3> = 'NOCHANGE'
    F(38) = 'XX.IN.INTMED.BANK' ; N(38) = '36'; T(38)<1> = 'A' ; T(38)<3> = 'NOINPUT'
    F(39) = 'MAILING' ; N(39) = '4..C' ; T(39)<1> = '' ; T(39)<2> = 'BEN_CUST_BANK'
    T(39)<3> = 'NOCHANGE'
    F(40) = 'PAY.METHOD' ; N(40) = '2..C' ; T(40)<1> = '' ; T(40)<2> = 'TA_PT_PA_PP_AT_AI_PB_CH'
    T(40)<3> = 'NOCHANGE'
    F(41) = 'BEN.OUR.CHARGES' ; N(41) = '3..C' ; T(41)<1> = '' ; T(41)<2> = 'BEN_OUR_SHA'
    T(41)<3> = 'NOCHANGE'
    F(42) = 'IN.BEN.OUR.CHARGES' ; N(42) = '3' ; T(42)<1> = 'A' ; T(42)<3> = 'NOINPUT'
    F(43) = 'CHARGES.ACCT.NO' ; N(43) = '16..C' ; T(43)<1> = 'ANT' ; T(43)<3> = 'NOCHANGE'
    CHECKFILE(43) = 'ACCOUNT': FM: AC.SHORT.TITLE: FM:'.A'
    F(44) = 'CHARGE.COM.DISPLAY' ; N(44) = '2..C' ; T(44)<1> = '' ; T(44)<2> = 'Y_NO'
    T(44)<3> = 'NOCHANGE'
    F(45) = 'COMMISSION.CODE' ; N(45) = '20..C' ; T(45)<1> = '' ; T(45)<3> = 'NOCHANGE'
    T(45)<2> = 'CREDIT LESS CHARGES_DEBIT PLUS CHARGES_WAIVE'
    F(46) = 'XX<COMMISSION.TYPE' ; N(46) = '11..C' ;  T(46)<1> = 'A' ; T(46)<3> = 'NOCHANGE' ; T(46)<12> = 'CLEAR'
    CHECKFILE(46) = 'FT.COMMISSION.TYPE': FM: FT4.DESCRIPTION: FM:'L.A'
    F(47) = 'XX-COMMISSION.AMT' ; N(47) = '022..C' ; T(47)<1> = 'AMT' ; T(47)<2> = '':VM:'C' ; T(47)<12> = 'CLEAR'
    T(47)<3> = 'NOCHANGE'
    F(48) = 'XX>COMMISSION.FOR' ; N(48) = '8..C' ; T(48)<1> = '' ; T(48)<2> = 'SENDER_RECEIVER' ; T(48)<12> = 'CLEAR'
    T(48)<3> = 'NOCHANGE'
    F(49) = 'CHARGE.CODE' ; N(49) = '20..C' ; T(49)<1> = '' ; T(49)<3> = 'NOCHANGE'
    T(49)<2> = 'CREDIT LESS CHARGES_DEBIT PLUS CHARGES_WAIVE'
    F(50) = 'XX<CHARGE.TYPE' ; N(50) = '11..C' ; T(50)<1> = 'A' ;T(50)<3> = 'NOCHANGE'
    CHECKFILE(50) = 'FT.CHARGE.TYPE': FM: FT5.DESCRIPTION: FM:'L.A'
    F(51) = 'XX-CHARGE.AMT' ; N(51) = '022..C' ; T(51)<1> = 'AMT' ; T(51)<2> = '':VM:'C'
    T(51)<3> = 'NOCHANGE'
    F(52) = 'XX>CHARGE.FOR' ; N(52) = '8..C' ; T(52)<1> = '' ; T(52)<2> = 'SENDER_RECEIVER'
    T(52)<3> = 'NOCHANGE'
    F(53) = 'CUSTOMER.SPREAD' ; N(53) = '016..C' ; T(53)<1> = 'R' ; T(53)<2> = '-' ; T(53)<3> = 'NOCHANGE' ; T(53)<9> = 'HOT.FIELD'
    F(54) = 'BASE.CURRENCY' ; N(54) = '3' ; T(54)<1> = 'CCY' ; T(54)<3> = 'NOCHANGE'
    CHECKFILE(54) = 'CURRENCY': FM: EB.CUR.CCY.NAME: FM:'L.A'
    F(55) = 'PROFIT.CENTRE.CUST' ; N(55) = '10' ; T(55)<1> = 'CUS' ; T(55)<3> = 'NOCHANGE'
    CHECKFILE(55) = 'CUSTOMER':FM:EB.CUS.SHORT.NAME:FM:'.A'
    F(56) = 'PROFIT.CENTRE.DEPT' ; N(56) = '4' ; T(56)<1> = 'DAO' ; T(56)<3> = 'NOCHANGE'
    CHECKFILE(56) = 'DEPT.ACCT.OFFICER':FM:EB.DAO.NAME:FM:'.A'
    F(57) = 'RETURN.TO.DEPT' ; N(57) = '2' ; T(57)<1> = '' ; T(57)<2> = 'Y_NO' ; T(57)<3> = 'NOCHANGE'
    F(58) = 'PRIORITY.TXN' ; N(58) = '1..C' ; T(58)<1> = '' ; T(58)<2> = 'P_U' ; T(58)<3> = 'NOCHANGE'
*
* N parameter of fields BK.TO.BK.INFO, IN.BK.TO.BK, BK.TO.BK.OUT
* changed to accept more than one space
    F(59) = 'XX.BK.TO.BK.INFO' ; N(59) = ' 35..C' ;T(59)<1> = 'SW' ; T(59)<3> = ""
    T(59)<3> = 'NOCHANGE'
    F(60) = 'XX.IN.BK.TO.BK' ; N(60) = ' 35' ; T(60)<1> = 'A' ; T(60)<3> = GTSNOINPUT
    F(61) = 'EXPOSURE.DATE' ; N(61) = '11..C' ; T(61)<1> = 'D' ; T(61)<3> = 'NOCHANGE'
    F(62) = 'FED.FUNDS' ; N(62) = '2..C' ; T(62)<1> = '' ; T(62)<2> = 'Y_NO' ; T(62)<3> = 'NOCHANGE'
* Pos type made as inputtable to support multi gaap when PL.CATEGORY exist in any one of the legs.
    F(63) = 'POSITION.TYPE' ; N(63) = '2' ; T(63)<1> = 'SSS'
    CHECKFILE(63) = 'FX.POS.TYPE': FM: FX.PT.DESCRIPTION: FM:'L.A'
    F(64) = 'NO.OF.BATCH.ITEMS' ; N(64) = '2' ; T(64)<3> = 'NV.EXTERN'
    F(65) = 'XX.FREE.TEXT.MSGTO' ; N(65) = '35..C' ; T(65)<1> = 'CUST.BIC' ; T(65)<2,1> = 'S'
    T(65)<2,2> = 'CUS' ; T(65)<3> = 'NOCHANGE'
    F(66) = 'XX.MESSAGE' ; N(66) = '54..C' ; T(66)<1> = 'S' ; T(66)<3> = 'NOCHANGE'
    F(67) = 'XX.LOCAL.REF' ; N(67) = '35' ; T(67)<1> = 'A' ; T(67)<3> = 'NOCHANGE'
    F(68) = 'XX<TAX.TYPE' ; N(68) = '2' ;  T(68)<1> = 'A' ; T(68)<3> = 'NOINPUT'
    CHECKFILE(68) = 'TAX': FM: EB.TAX.DESCRIPTION: FM:'L.A..D'
    F(69) = 'XX>TAX.AMT' ; N(69) = '22' ;T(69)<1> = 'AMT' ; T(69) <2> = '':VM:'C'
    T(69)<3> = 'NOINPUT'
    F(70) = 'AMOUNT.DEBITED' ; N(70) = '22' ; T(70)<1> = 'AMT' ; T(70) <2> = '':VM:'C'
    T(70)<3> = 'NOINPUT'
    F(71) = 'AMOUNT.CREDITED' ; N(71) = '22' ; T(71)<1> = 'AMT' ; T(71) <2> = '':VM:'C'
    T(71)<3> = 'NOINPUT'
    F(72) = 'TOTAL.CHARGE.AMT' ; N(72) = '22' ; T(72)<1> = 'AMT' ; T(72) <2> = '':VM:'C'
    T(72)<3> = 'NOINPUT'
    F(73) = 'TOTAL.TAX.AMOUNT' ; N(73) = '22' ; T(73)<1> = 'AMT' ; T(73) <2> = '':VM:'C'
    T(73)<3> = 'NOINPUT'
    F(74) = 'CUSTOMER.RATE' ; N(74) = '16..C' ; T(74)<1> = 'R' ; T(74)<9> = 'HOT.FIELD'
    F(75) = 'XX.IN.REC.CORR.BK' ; N(75) = '35'    ;* Field length increased to 35 chars - BG_100002640 -
    T(75)<1> = 'A' ; T(75)<3> = GTSNOINPUT
    F(76) = 'INWARD.PAY.TYPE' ; N(76) = '55' ; T(76)<1> = 'A' ; T(76)<3> = GTSNOINPUT
    F(77) = 'XX.IN.SEND.CORR.BK' ; N(77) = '35' ; T(77)<1> = 'A' ; T(77)<3> = GTSNOINPUT
    F(78) = 'TELEX.FROM.CUST' ; N(78) = '35' ;T(78)<1> = 'A' ; T(78)<3> = GTSNOINPUT
    F(79) = 'DELIVERY.INREF' ; N(79) = '35' ; T(79)<1> = 'A' ; T(79)<3> = GTSNOINPUT
    F(80) = 'XX.DELIVERY.OUTREF' ; N(80) = '46' ; T(80)<1> = 'A' ; T(80)<3> = 'NOINPUT'
    F(81) = 'CREDIT.COMP.CODE' ; N(81) = '9' ; T(81)<1> = 'A' ; T(81)<3> = 'NOINPUT'
    CHECKFILE(81) = 'COMPANY':FM:EB.COM.COMPANY.NAME:FM:'L.A'
    T(81)<4> = '##-###-####'
    F(82) = 'DEBIT.COMP.CODE' ; N(82) = '9' ; T(82)<1> = 'A' ; T(82)<3> = 'NOINPUT'
    CHECKFILE(82) = 'COMPANY':FM:EB.COM.COMPANY.NAME:FM:'L.A'
    T(82)<4> = '##-###-####'
    F(83) = 'STATUS' ; N(83) = '7' ; T(83)<1> = 'A' ; T(83)<3> = 'NOINPUT'
    F(84) = 'DELIVERY.MKR' ; N(84) = '1' ; T(84)<1> = 'A' ; T(84)<3> = 'NOINPUT'
    F(85) = 'ORD.CUST.CODE ' ; N(85) = ' 35..C' ; T(85)<1> = 'SW' ; T(85)<3> = 'NOCHANGE'
    F(86) = 'ACCT.WITH.BK.ACNO' ; N(86) = '34' ;T(86)<1> = 'A' ; T(86)<3> = 'NOINPUT'
    F(87) = 'LOC.AMT.DEBITED' ; N(87) = '18' ; T(87)<1> = 'AMT'
    T(87)<2,2> = R.COMPANY(EB.COM.LOCAL.CURRENCY) ; T(87)<3> = 'NOINPUT'
    F(88) = 'LOC.AMT.CREDITED' ; N(88) = '18' ;  T(88)<1> = 'AMT'
    T(88)<2,2> = R.COMPANY(EB.COM.LOCAL.CURRENCY) ; T(88)<3> = 'NOINPUT'
    F(89) = 'LOC.TOT.TAX.AMT' ; N(89) = '18' ; T(89)<1> = 'AMT'
    T(89)<2,2> = R.COMPANY(EB.COM.LOCAL.CURRENCY) ; T(89)<3> = 'NOINPUT'
    F(90) = 'LOCAL.CHARGE.AMT' ; N(90) = '18' ; T(90)<1> = 'AMT'
    T(90)<2,2> = R.COMPANY(EB.COM.LOCAL.CURRENCY) ; T(90)<3> = 'NOINPUT'
    F(91) = 'LOC.POS.CHGS.AMT' ; N(91) = '18' ; T(91)<1> = 'AMT'
    T(91)<2,2> = R.COMPANY(EB.COM.LOCAL.CURRENCY) ; T(91)<3> = 'NOINPUT'
    F(92) = 'MKTG.EXCH.PROFIT' ; N(92) = '18' ; T(92)<1> = 'AMT'
    T(92)<2,2> = R.COMPANY(EB.COM.LOCAL.CURRENCY) ; T(92)<3> = 'NOINPUT'
    F(93) = 'RATE.INPUT.MKR' ; N(93) = '15' ; T(93)<1> = 'A' ; T(93)<3> = 'NOINPUT'
    F(94) = 'CUST.GROUP.LEVEL' ; N(94) = '15' ; T(94)<1> = 'A' ; T(94)<3> = 'NOINPUT'
    F(95) = 'DEBIT.CUSTOMER' ; N(95) = '10' ; T(95)<1> = 'CUS' ; T(95)<3> = 'NOINPUT'
    CHECKFILE(95) = 'CUSTOMER':FM:EB.CUS.SHORT.NAME:FM:'.A'
    F(96) = 'CREDIT.CUSTOMER' ; N(96) = '10' ; T(96)<1> = 'CUS' ; T(96)<3> = 'NOINPUT'
    CHECKFILE(96) = 'CUSTOMER':FM:EB.CUS.SHORT.NAME:FM:'.A'
    F(97) = 'SEND.PAYMENT.Y.N' ; N(97) = '2..C' ; T(97)<1> = '' ; T(97)<2> = 'Y_NO'
    F(98) = 'DR.ADVICE.REQD.Y.N' ; N(98) = '2..C' ; T(98)<1> = '' ; T(98)<2> = 'Y_NO'
    F(99) = 'CR.ADVICE.REQD.Y.N' ; N(99) = '2..C' ; T(99)<1> = '' ; T(99)<2> = 'Y_NO'
    F(100) = 'DEAL.MARKET' ; N(100) = '2..C' ; T(100)<1> = ''
    CHECKFILE(100) = 'CURRENCY.MARKET':FM:EB.CMA.DESCRIPTION:FM:'L.A'
    F(101) = 'CHARGED.CUSTOMER' ; N(101) = '10' ; T(101)<1> = 'CUS'
    CHECKFILE(101) = 'CUSTOMER':FM:EB.CUS.SHORT.NAME:FM:'.A'
    F(102) = 'XX.IN.REASON.OVE' ; N(102) = '60' ; T(102) = "A" ; T(102)<3> = GTSNOINPUT   ;* BG_10000097
    F(103) = 'DEALER.DESK' ; N(103) = '002..C' ; T(103)<1> = 'A' ; T(103)<3> = 'NOCHANGE'
    CHECKFILE(103) = "DEALER.DESK":FM:FX.DD.DESCRIPTION:FM:"L.A"
    F(104) = 'RECALC.FWD.RATE' ; N(104) = '' ; T(104)<1> = '' ; T(104)<3> = 'NOINPUT'
***
* To conserve sanity, from this point onwards, fields are added
* using the increment z counter methods from the modern template
*
* This is done on the back of GB9900582 which is adding RETURN.CHEQUE
* and DRAWN.ACCOUNT and 12 reserved fields for future use.
***
    Z = 104
    Z+=1 ; F(Z) = 'RETURN.CHEQUE' ; N(Z) = "3..C" ; T(Z)= FM : 'YES_NO' : FM : 'NOINPUT'
    Z+=1 ; F(Z) = 'DRAWN.ACCOUNT' ; N(Z) = "16..C" ; T(Z) = 'ACC' : FM : FM : 'NOINPUT'
    CHECKFILE(Z) = 'ACCOUNT': FM: AC.SHORT.TITLE: FM:'.A'   ;* GB9900582
    Z+=1 ; F(Z) = "ACCOUNTING.DATE" ; N(Z) = "11" ; T(Z) = "D" ; T(Z)<3> = ""
    LOCATE "PC" IN R.COMPANY(EB.COM.APPLICATIONS)<1,1> SETTING PC.POS ELSE
        T(Z)<3> = "NOINPUT"
    END
    Z+=1 ; F(Z) = "XX.INSTRCTN.CODE" ; N(Z) = "35" ; T(Z)<1> = "A"    ;* EN_10000786 - s/e - Modified N(
    Z+=1 ; F(Z) = "COLL.REM.BK" ; N(Z) = "35..C"
    T(Z)<1> = 'CUST.BIC' ; T(Z)<2,1> = 'SW' ; T(Z)<2,2> = 'BIC'       ;* CI_10009968 - S/E
    Z+=1 ; F(Z) = "EXPECTED.RECS.ID" ; N(Z) = "16" ; T(Z)<1> = "A" ; T(Z)<3> = "NOINPUT"
    Z+=1 ; F(Z) = "TOT.REC.COMM" ; N(Z) = "15" ; T(Z)<1> = "AMT" ; T(Z)<3> = "NOINPUT"
    Z+=1 ; F(Z) = "TOT.REC.COMM.LCL" ; N(Z) = "15" ; T(Z)<1> = 'AMT'
    T(Z) <2,2> = R.COMPANY(EB.COM.LOCAL.CURRENCY) ; T(Z)<3> = 'NOINPUT'         ;* CI_10015622 S/E
    Z+=1 ; F(Z) = "TOT.REC.CHG" ; N(Z) = "15" ; T(Z)<1> = "AMT" ; T(Z)<3> = "NOINPUT"
    Z+=1 ; F(Z) = "TOT.REC.CHG.LCL" ; N(Z) = "15" ; T(Z)<1> = 'AMT'
    T(Z) <2,2> = R.COMPANY(EB.COM.LOCAL.CURRENCY) ; T(Z)<3> = 'NOINPUT'         ;* CI_10015622 S/E
** GLOBUS_EN_10000353 -S
    Z+=1 ; F(Z) = "CHEQ.TYPE" ; N(Z) = "4..C" ; T(Z) = 'SSS'
    CHECKFILE(Z) = 'CHEQUE.TYPE':FM:CHEQUE.TYPE.DESCRIPTION
** GLOBUS_EN_10000353 -E
    Z+=1 ; F(Z) = 'XX<RELATED.MSG' ; N(Z) = "8" ; T(Z)<2> = 'PAYMENT_COVER'     ;* EN_10001119 - S
    Z+=1 ; F(Z) = 'XX>XX.TIME.IND' ; N(Z) = "14" ; T(Z)<1> = 'A'      ;* EN_10001119 - E
    Z+=1 ; F(Z) = 'XX.IN.TIME.IND' ; N(Z) = "35" ; T(Z)<1> = 'A' ; T(Z)<3> = GTSNOINPUT
** GLOBUS_EN_10000977 -S
    Z+=1 ; F(Z) = 'XX<SEND.TO.PARTY' ; N(Z) = "35..C" ; T(Z) = '' ; T(Z)<2> = 'DRCUST_CRCUST_RCVBK_COLL.REM.BK' ; T(Z)<3> = 'NOCOPY'
* EN_10001611 - s
* N parameter changed to accept more than one space
    Z+=1 ; F(Z) = 'XX-XX.BK.TO.BK.OUT' ; N(Z) = " 35..C" ; T(Z)<1> = 'SW' ; T(Z)<3> = 'NOCOPY'
* EN_10001611 - e
    Z+=1 ; F(Z) = 'XX>MESSAGE.TYPE' ; N(Z) = "3" ; T(Z) = 'A' ; T(Z)<3> = "NOINPUT"
** GLOBUS_EN_10000977 -E
* EN_10001033 - S
    Z+=1 ; F(Z) = 'REVERSAL.MKR ' ; N(Z) = "3" ; T(Z)<1> = 'A' ; T(Z)<3> = 'NOINPUT'
* EN_10000786 - s
* Added new fields for Inward Delivery Processing and few more reversed fields.
    Z+=1 ; F(Z) = 'RELATED.REF' ; N(Z) = "35" ; T(Z)<1> = 'A' ; T(Z)<3> = GTSNOINPUT
    Z+=1 ; F(Z) = 'XX.IN.INSTR.CODE' ; N(Z) = "35" ; T(Z)<1> = 'A' ; T(Z)<3> = GTSNOINPUT
    Z+=1 ; F(Z) = 'XX.IN.PROCESS.ERR' ; N(Z) = "65" ; T(Z)<1> = 'A' ; T(Z)<3> = GTSNOINPUT
    Z+=1 ; F(Z) = 'XX.IN.SWIFT.MSG' ; N(Z) = "65" ; T(Z)<1> = "A" ; T(Z)<3> = GTSNOINPUT
* EN_10001322 - S
    Z+=1 ; F(Z) = 'ACCT.WITH.BANK.ACC' ; N(Z) = "34" ; T(Z)<1> = 'A'
    Z+=1 ; F(Z) = 'IN.ACCT.BANK.ACC' ; N(Z) = "34" ; T(Z)<1> = 'A' ; T(Z)<3> = GTSNOINPUT
    Z+=1 ; F(Z) = 'REC.CORR.BANK.ACC' ; N(Z) = "34" ; T(Z)<1> = 'A'
    Z+=1 ; F(Z) = 'IN.REC.CORR.ACC' ; N(Z) = "34" ; T(Z)<1> = 'A' ; T(Z)<3> = GTSNOINPUT
    Z+=1 ; F(Z) = 'INTERMED.BANK.ACC' ; N(Z) = "34" ; T(Z)<1> = 'A'
    Z+=1 ; F(Z) = 'IN.INTERMED.ACC' ; N(Z) = "34" ; T(Z)<1> = 'A' ; T(Z)<3> = GTSNOINPUT
* EN_10001322 - E
    Z+=1 ; F(Z) = 'INSTRUCTED.AMT' ; N(Z) = "21" ; T(Z)<1> = 'A' ; T(Z)<3> = GTSNOINPUT   ;* BG_10000218
* BG_100002640 - s
    Z+=1 ; F(Z) = 'XX.IN.INTERMED.BK' ; N(Z) = "35" ; T(Z)<1> = 'A' ; T(Z)<3> = GTSNOINPUT
    Z+=1 ; F(Z) = 'IN.EXCH.RATE' ; N(Z) = "16" ; T(Z)<1> = 'A' ; T(Z)<3> = GTSNOINPUT
* BG_100002640 - e
    Z += 1 ; F(Z) = 'RATE.FIXING' ; N(Z) = '3..C' ; T(Z)<2>= "YES_NO" ;* BG_100001401 S/E
* EN_10000786 - e
* EN_10001611 - S
* Introducing new fields for Third Reimbeshment Institution
* New Fields are COVER.METHOD,IN.THIRD.REIMB.BK,THIRD.REIMB.ACC
* BG_100003558 s/e
* Add C in the N parameter of Cover method
    Z+=1 ; F(Z) = 'COVER.METHOD' ; N(Z) = "15..C" ; T(Z)<2> = 'SERIAL_COVER-DIRECT_COVER-NEAR_THIRD-INST'
    Z+=1 ; F(Z) = 'XX.IN.3RD.REIMB.BK' ; N(Z) = "35" ; T(Z)<1> ="A" ; T(Z)<3> = GTSNOINPUT
    Z+=1 ; F(Z) = 'IN.3RD.REIMB.ACC' ; N(Z) ="34" ; T(Z)<1> = "A" ; T(Z)<3> = GTSNOINPUT
* EN_10001616 - S
    Z += 1 ; F(Z) = 'MT103.TYPE' ; N(Z) = '25' ; T(Z)<2> = 'MT103+_MT103EXTEND'
    Z += 1 ; F(Z) = 'EXTEND.FORMAT' ; N(Z) = '35' ; T(Z)<2> = 'ANSI_NARR_SWIF_UEDI'
    Z += 1 ; F(Z) = 'XX.EXTEND.INFO' ; N(Z) = '65' ; T(Z)<1> = 'A' ; T(Z)<7> = 'TEXT'
* EN_10001616 - E
* BG_100003401  S
    Z+=1 ; F(Z) = 'RATE.FIXING.IND' ; N(Z) = "2" ; T(Z)<2> = 'Y_NO'   ;* EN_10002373 S/E
* BG_100003401 E
* EN_10001649 - S
    Z+=1 ; F(Z) = 'INW.SEND.BIC' ; N(Z) = "35" ; T(Z)<1> = 'A' ; T(Z)<3> = GTSNOINPUT
    Z+=1 ; F(Z) = 'XX.IN.SEND.CHG' ; N(Z) = "35" ; T(Z)<1> = 'A' ; T(Z)<3> = GTSNOINPUT   ;* CI_10014215
    Z+=1 ; F(Z) = 'IN.REC.CHG' ; N(Z) = "35" ; T(Z)<1> = 'AMT' ; T(Z)<2> = '':VM:'C' ; T(Z)<3> = GTSNOINPUT
    Z+=1 ; F(Z) = 'AC.CHG.REQ.ID' ; N(Z) = "35" ; T(Z)<1> = 'A' ; T(Z)<3> = GTSNOINPUT
    Z+=1 ; F(Z) = 'TOT.REC.CHG.CRCCY' ; N(Z) = "19" ; T(Z)<1> = 'AMT' ; T(Z)<2> = '':VM:FT.CREDIT.CURRENCY ; T(Z)<3> = GTSNOINPUT
    Z+=1 ; F(Z) = 'TOT.SND.CHG.CRCCY' ; N(Z) = "19" ; T(Z)<1> = 'AMT' ; T(Z)<2> = '':VM:FT.CREDIT.CURRENCY ; T(Z)<3> = GTSNOINPUT
    Z+=1 ; F(Z) = 'CHG.ADVICE.MSG' ; N(Z) = "35" ; T(Z)<3> = 'NOINPUT'
* EN_10001649 - E
* EN_10001656 - S
    Z+=1 ; F(Z) = "EXPECTED.COVER.ID" ; N(Z) = "25" ; T(Z) = '' ; T(Z)<1> = "A" ; T(Z)<3> = "NOINPUT"
* EN_10001656 - E
* EN_10001661 S
    Z+=1 ; F(Z) = 'NETTING.STATUS' ; N(Z) = "7" ; T(Z)<2> = '102_203_102*203_N_'          ;* 10002261 BG
* EN_10001661 S
    Z+=1 ; F(Z) = 'AUTH.DATE' ; N(Z) = "11" ; T(Z) = "D" ; T(Z)<3> = 'NOINPUT'
    Z+=1 ; F(Z) = 'BK.OPERATION.CODE' ; N(Z) = "6" ; T(Z)<2> = "CREDIT_CHQB_CRED_CRTS_SPAY_SPRI_SSTD_"
    Z+=1 ; F(Z) = 'AM.INFLOW.RATE' ; N(Z) = "16..C" ; T(Z)<1> = "R"   ;* EN_10002272 S/E
    Z+=1 ; F(Z) = 'PARENT.TXN.ID' ; N(Z) = "35..C" ; T(Z) = 'A' ; T(Z)<3> = 'EXTERN'      ;* EN_10002339
    Z+=1 ; F(Z) = 'ROUND.TYPE' ; N(Z) = "20..C" ; T(Z) = 'A'
    CHECKFILE(157) = "EB.ROUNDING.RULE":FM:EB.RDGR.DESCRIPTION:FM:".A"
    Z+=1 ; F(Z) = 'BENEFICIARY.ID' ; N(Z) = "17..C" ; T(Z) = 'A'
    CHECKFILE(Z) = "BENEFICIARY":FM:ARC.BEN.NICKNAME:FM:'L'
*Reserved.4 & .3 is used for the new fields MSG.TYPE & MSG.DATE resp.
    Z+=1 ; F(Z) = 'XX<MSG.TYPE' ; N(Z) = "35"  ; T(Z) = 'A' ;  T(Z)<3> = 'NOINPUT'
* EN_10001611 - E
    Z+=1 ; F(Z) = 'XX>MSG.DATE' ; N(Z) = "8" ; T(Z)='D' ; T(Z)<3> = 'NOINPUT'

    Z += 1 ; F(Z) = 'XX.SIGNATORY' ; N(Z) = "35" ; T(Z) = 'CUS'
    CHECKFILE(Z) = "CUSTOMER" : FM : EB.CUS.SHORT.NAME : FM : "L"
* Fields introduced for storing CARD informations:
    Z += 1 ; F(Z) = 'CARD.NUMBER' ; N(Z) = "21" ; T(Z) = 'A'
    CHECKFILE(Z) = "CARD.ISSUE":FM:CARD.IS.NAME:FM:"L"
    Z += 1 ; F(Z) = 'XX.CARD.TXN.DETAIL' ; N(Z) = "25" ; T(Z) = 'A'
    Z+=1 ; F(Z) = 'XX.IN.C.ORD.BK' ; N(Z) = "35" ; T(Z) = 'A' ; T(Z)<3> = 'GTSNOINPUT'    ;* IN field of Ordering bank field for 202C/205C message's 'B' tags
    Z+=1 ; F(Z) = 'XX.IN.C.INTMED.BK' ; N(Z) = "35" ; T(Z) = 'A' ; T(Z)<3> = 'GTSNOINPUT' ;* IN field of Intermediary bank field for 202C/205C message's 'B' tags
    Z+=1 ; F(Z) = 'XX.IN.C.ACC.WIT.BK' ; N(Z) = "35" ; T(Z) = 'A' ; T(Z)<3> = 'GTSNOINPUT'          ;* IN field of Account with bank field for 202C/205C message's 'B' tags
    Z+=1 ; F(Z) = 'XX.IN.C.BK.T.BK.IN' ; N(Z) = " 35" ; T(Z) = 'A' ; T(Z)<3> = 'GTSNOINPUT'         ;* IN field of Bank to bank info field for 202C/205C message's 'B' tags
    Z+=1 ; F(Z) = 'TFS.REFERENCE' ; N(Z) = "35" ; T(Z)<1> = 'A' ; T(Z)<3> = GTSNOINPUT
    Z+=1 ; F(Z) = 'CHEQUE.DRAWN' ; N(Z) = "35" ; T(Z) = 'A'
    Z+=1 ; F(Z) = 'ISSUE.CHEQUE.TYPE' ; N(Z) = '4..C' ; T(Z) = 'SSS'
    CHECKFILE(Z) = "CHEQUE.TYPE":FM:CHEQUE.TYPE.DESCRIPTION:FM:"L"
    Z+=1 ; F(Z) = 'STOCK.NUMBER' ; N(Z) = '20..C'
    Z+=1 ; F(Z) = 'PAYEE.NAME' ; N(Z) = '50..C' ; T(Z) = 'A'
    Z+=1 ; F(Z) = "STOCK.REGISTER" ; N(Z) = "20..C" ; T(Z) = "A"
    CHECKFILE(Z) = "STOCK.REGISTER":FM:FM:"L"
    Z+=1 ; F(Z) = "SERIES.ID" ; N(Z) = "30..C" ; T(Z) = "A"
*
    Z+=1 ; F(Z) = 'ORD.CUST.ACCT' ; N(Z) = '35' ; T(Z) = 'SW'
    Z+=1 ; F(Z) = 'IN.ORD.CUST.ACCT' ; N(Z) = '35' ; T(Z) = 'A' ; T(Z)<3> = GTSNOINPUT
    Z+=1 ; F(Z) = 'IBAN.DEBIT' ; N(Z) = '35' ; T(Z) = 'A'
    Z+=1 ; F(Z) = 'IBAN.CREDIT' ; N(Z) = '35' ; T(Z) = 'A'
    Z+=1 ; F(Z) = 'IBAN.BEN' ; N(Z) = '35..C' ; T(Z) = 'A' ; T(Z)<9> = 'HOT.FIELD'
    Z+=1 ; F(Z) = 'IBAN.ACCT.WITH.BANK' ; N(Z) = '35' ; T(Z) = 'A'
    Z+=1 ; F(Z) = 'IBAN.REC.CORR.BANK' ; N(Z) = '35' ; T(Z) = 'A'
    Z+=1 ; F(Z) = 'IBAN.INTERMED.BANK' ; N(Z) = '35' ; T(Z) = 'A'
    Z+=1 ; F(Z) = 'IBAN.ORD.CUST.ACC' ; N(Z) = '35' ; T(Z) = 'A'
    Z+=1 ; F(Z) = 'IBAN.CHARGES.ACCT' ; N(Z) = '35' ; T(Z) = 'A'
    Z+=1 ; F(Z) = 'BIC.IBAN.BEN' ; N(Z) = '12' ; T(Z) = 'SW' ; T(Z)<3> = 'NOINPUT' ;* BIC code for IBAN.BEN
    Z+=1 ; F(Z) = 'XX.BIC.IBAN.BEN.NAME' ; N(Z) = '35' ; T(Z)="SW" ; T(Z)<3> = 'NOINPUT' ; T(Z)<7>="TEXT" ;* IBAN.BEN Instuation details
    Z+=1 ; F(Z) = 'BIC.IBAN.BEN.CITY' ; N(Z) = '35' ; T(Z) = 'SW' ; T(Z)<3> = 'NOINPUT' ;* IBAN.BEN
    Z+=1 ; F(Z) = 'RESERVED.7' ; N(Z) = '35' ; T(Z) = 'A' ; T(Z)<3> = 'NOINPUT'
    Z+=1 ; F(Z) = 'RESERVED.6' ; N(Z) = '35' ; T(Z) = 'A' ; T(Z)<3> = 'NOINPUT'
    Z+=1 ; F(Z) = 'RESERVED.5' ; N(Z) = '35' ; T(Z) = 'A' ; T(Z)<3> = 'NOINPUT'
    Z+=1 ; F(Z) = 'RESERVED.4' ; N(Z) = '35' ; T(Z) = 'A' ; T(Z)<3> = 'NOINPUT'
    Z+=1 ; F(Z) = 'RESERVED.3' ; N(Z) = '35' ; T(Z) = 'A' ; T(Z)<3> = 'NOINPUT'
    Z+=1 ; F(Z) = 'RESERVED.2' ; N(Z) = '35' ; T(Z) = 'A' ; T(Z)<3> = 'NOINPUT'
    Z+=1 ; F(Z) = 'RESERVED.1' ; N(Z) = '35' ; T(Z) = 'A' ; T(Z)<3> = 'NOINPUT'
    Z+=1 ; F(Z) = 'XX.STMT.NOS' ; N(Z) = "35" ; T(Z)<1> = 'A' ; T(Z)<3> = 'NOINPUT'
    Z+=1 ; F(Z) = 'XX.OVERRIDE' ; N(Z) = "35" ; T(Z)<1> = 'A' ; T(Z)<3> = 'NOINPUT'

    LOCATE "IN" IN R.SPF.SYSTEM<SPF.PRODUCTS,1> SETTING POS ELSE        ;* Is product code installed
        FOR I = FT.IBAN.DEBIT TO FT.IBAN.CHARGES.ACCT
            T(I)<3>="NOINPUT"
        NEXT I
    END
*
*Reset Field Lengths for certain Account Fields -GB0002441
*
*
    OBJECT.ID="ACCOUNT" ; MAX.LEN=""
    CALL EB.GET.OBJECT.LENGTH(OBJECT.ID,MAX.LEN)
    IF MAX.LEN NE "" THEN
        N(2)=MAX.LEN:'.':N(2)['.',2,2]  ;*Debit Acct Number...
        N(11)=MAX.LEN:'.':N(11)['.',2,2]          ;*Credit Acct number...
    END
*
    DELETE.FLAG = 0
    V = Z+9         ;*FT.AUDIT.DATE.TIME             ; * number of fields
*
    RETURN
*
    END
*
