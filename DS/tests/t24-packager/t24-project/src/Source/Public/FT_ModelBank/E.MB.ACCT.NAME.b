*-----------------------------------------------------------------------------
* <Rating>-30</Rating>
*-----------------------------------------------------------------------------
* Description:
*             Default the customer name in Beneficiary creation Screen.
*------------------------------------------------------------------------------
*Modification History
*-----------------------------------------------------------------------------
    SUBROUTINE E.MB.ACCT.NAME

    $INSERT I_COMMON
    $INSERT I_EQUATE
    $INSERT I_F.CUSTOMER
    $INSERT I_F.ACCOUNT
    $INSERT I_F.BENEFICIARY
    $INSERT I_ENQUIRY.COMMON

    GOSUB INITIALISE
    GOSUB OPENFILE
    GOSUB PROCESS

    RETURN

INITIALISE:

    ACCOUNT.ID=R.NEW(ARC.BEN.BEN.ACCT.NO)

    FN.BENEF="F.BENEFICIARY"

    F.BENEF = " "

    FN.ACCOUNT = "F.ACCOUNT"

    F.ACCOUNT = " "

    FN.CUS = "F.CUSTOMER"

    F.CUS = " "

    RETURN

OPENFILE:

    CALL OPF(FN.ACCOUNT,F.ACCOUNT)

    CALL OPF(FN.CUS,F.CUS)

    CALL OPF(FN.BENEF,F.BENEF)

    RETURN

PROCESS:

    CALL F.READ(FN.ACCOUNT,ACCOUNT.ID,ACC.REC,F.ACCOUNT,ACC.ERR)

    CUS.ID= ACC.REC<AC.CUSTOMER>

    CALL F.READ(FN.CUS,CUS.ID,CUS.REC,F.CUS,CUS.ERR)

    CUS.NAME=CUS.REC<EB.CUS.NAME.1>

    R.NEW(ARC.BEN.DEFAULT.NARRATIVE) = CUS.NAME

    RETURN

END
