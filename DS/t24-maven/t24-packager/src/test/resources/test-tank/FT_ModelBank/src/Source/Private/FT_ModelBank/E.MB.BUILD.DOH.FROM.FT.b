*-----------------------------------------------------------------------------
* <Rating>-65</Rating>
*-----------------------------------------------------------------------------
    SUBROUTINE E.MB.BUILD.DOH.FROM.FT(ENQ.DATA)

*  Returns DE.O.HEADER ID for the given FT reference.
*-----------------------------------------------------------------------------
* Modification History:

* 08/10/09  -  BG_100023555
*              Initial Version
*
* 07/12/09  -  BG_100026088
*              Added a New Condition For Date Selection
*
* 1/2/12    - Task 348585
*             Need to check the record first in live and if that is not found
*             then check it in history.
*-----------------------------------------------------------------------------
    $INSERT I_COMMON
    $INSERT I_EQUATE
    $INSERT I_ENQUIRY.COMMON
    $INSERT I_F.DE.HEADER
    $INSERT I_F.FUNDS.TRANSFER
    $INSERT I_F.DATES
    $INSERT I_DAS.COMMON
    $INSERT I_DAS.FUNDS.TRANSFER

    GOSUB INIT
    GOSUB GET.SELECTION.CRITERIA
    GOSUB PROCESS
    RETURN

*--------------------------------------------------------------------
INIT:

* Initialise the variables

    FN.FT = ""
    F.FT = ""
    R.FT.REC = ""
    READ.ERR = ""
    HIS.READ.ERR = ""
    HIS.FLAG = ""
    FT.REF = ""
    RET.ARR = ""
    TRANS.DATE = ""
    SYS.JUL.DATE = ""

    RETURN

*--------------------------------------------------------------------
GET.SELECTION.CRITERIA:
* Get selection criteria details

    ENQ.DATA.FIELDS = ENQ.DATA<2>
    E.DATA = ENQ.DATA<4>
    E.OPERAND = ENQ.DATA<3>

    CONVERT VM TO FM IN ENQ.DATA.FIELDS
    CONVERT VM TO FM IN E.DATA

    LOCATE '@ID' IN ENQ.DATA.FIELDS SETTING POS THEN
        FT.REF = E.DATA<POS>
    END

    LOCATE 'BANK.DATE' IN ENQ.DATA.FIELDS SETTING POS THEN
        TRANS.DATE = E.DATA<POS>
    END

    SYS.JUL.DATE = R.DATES(EB.DAT.JULIAN.DATE)[3,5]

    RETURN

*--------------------------------------------------------------------
PROCESS:

* Get records based on fixed selection

    IF FT.REF NE '' THEN
        FN.FT = 'F.FUNDS.TRANSFER'
        F.FT = ''
        CALL OPF(FN.FT,F.FT)
        CALL F.READ(FN.FT,FT.REF,R.FT.REC,F.FT,READ.ERR)
        IF READ.ERR THEN
            FN.FT = 'F.FUNDS.TRANSFER$HIS'
            CALL OPF(FN.FT,F.FT)
            FT.REF = FT.REF:';1'
            CALL F.READ(FN.FT,FT.REF,R.FT.REC,F.FT,HIS.READ.ERR)
        END
        IF R.FT.REC NE "" THEN
            GOSUB GET.DEL.REF.FROM.FT
            GOSUB BUILD.ENQ.DATA
        END
    END

    IF TRANS.DATE NE '' THEN

        CALL CALC.JUL.DATE(TRANS.DATE)
        T.DATE = TRANS.DATE[3,5]

        IF T.DATE EQ SYS.JUL.DATE THEN
            DAS.LIST = dasFundsTransferIDlike
            DAS.ARGS = T.DATE
            CALL DAS("FUNDS.TRANSFER",DAS.LIST,DAS.ARGS,"")

            FN.FT = 'F.FUNDS.TRANSFER'
            F.FT = ''
            CALL OPF(FN.FT,F.FT)

        END ELSE
            DAS.LIST = dasFundsTransferIDlike
            DAS.ARGS = T.DATE
            CALL DAS("FUNDS.TRANSFER",DAS.LIST,DAS.ARGS,"$HIS")

            FN.FT = 'F.FUNDS.TRANSFER$HIS'
            F.FT = ''
            CALL OPF(FN.FT,F.FT)
        END
        FT.ARR = DAS.LIST
        NO.OF.FTS = DCOUNT(FT.ARR,@FM)

        LOOP
            REMOVE FT.ID FROM FT.ARR SETTING FT.POS
        WHILE FT.ID:FT.POS
            CALL F.READ(FN.FT,FT.ID,R.FT.REC,F.FT,FT.ERR)
            GOSUB GET.DEL.REF.FROM.FT
        REPEAT
        GOSUB BUILD.ENQ.DATA
    END

    RETURN
**************************************************************************
GET.DEL.REF.FROM.FT:
********************

    OUT.REF.IDS = R.FT.REC<FT.DELIVERY.OUTREF>
    NO.OF.DELS = DCOUNT(OUT.REF.IDS,@VM)
    LOOP
        REMOVE DEL.ID FROM OUT.REF.IDS SETTING DEL.ID.POS
    WHILE DEL.ID:DEL.ID.POS
        RET.ARR<-1> = FIELD(DEL.ID,'-',1)
    REPEAT

    RETURN
**************************************************************************
BUILD.ENQ.DATA:
***************

    CONVERT FM TO " " IN RET.ARR
    ENQ.DATA<2> = '@ID'
    ENQ.DATA<3> = 'EQ'
    ENQ.DATA<4> = RET.ARR

    RETURN
**************************************************************************
END
