*-----------------------------------------------------------------------------
* <Rating>-20</Rating>
*-----------------------------------------------------------------------------
*
*-----------------------------------------------------------------------------
    SUBROUTINE V.MB.AI.DEF.DT.CCY
*-----------------------------------------------------------------------------
* Validation routine to default Debit currency corresponding to Debit account
*-----------------------------------------------------------------------------
* Modification History:
*
* 15/09/08 - BG_100019952
*            Routine restructured
*-----------------------------------------------------------------------------

    $INSERT I_COMMON
    $INSERT I_EQUATE
    $INSERT I_F.VERSION
    $INSERT I_F.FUNDS.TRANSFER
    $INSERT I_F.ACCOUNT

    GOSUB INITIALISE
    GOSUB PROCESS

    RETURN

***********
INITIALISE:
***********
    FN.ACC = 'F.ACCOUNT'
    F.ACC = ''
    CALL OPF(FN.ACC,F.ACC)

    RETURN

*********
PROCESS:
*********

    DEBIT.ACC = R.NEW(FT.DEBIT.ACCT.NO)
    CALL F.READ(FN.ACC,DEBIT.ACC,R.ACC,F.ACC,R.ACC.ERR)
    IF NOT(R.ACC.ERR) THEN
        DEBIT.CCY = R.ACC<AC.CURRENCY>
        R.NEW(FT.DEBIT.CURRENCY) = DEBIT.CCY
    END

    RETURN
