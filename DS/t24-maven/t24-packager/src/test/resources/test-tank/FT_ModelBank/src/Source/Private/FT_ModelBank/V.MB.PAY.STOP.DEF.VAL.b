*-----------------------------------------------------------------------------
* <Rating>-23</Rating>
*-----------------------------------------------------------------------------
****************************************************************************************************
* Validation routine for Payment stop version
****************************************************************************************************
    SUBROUTINE V.MB.PAY.STOP.DEF.VAL
****************************************************************************************************
* 29/02/2012- New Development
* Purpose - The routine to default Beneficiary,Cheque type and Amount values to corresponding Draft number 
*           inputted in the Payment stop version.
* Developed By - Rekha M
****************************************************************************************************

     $INSERT I_COMMON
     $INSERT I_EQUATE
     $INSERT I_F.VERSION
     $INSERT I_F.CHEQUE.REGISTER.SUPPLEMENT
     $INSERT I_F.PAYMENT.STOP

     GOSUB INITIALISE
     GOSUB PROCESS

     RETURN

 INITIALISE:


     FN.CHEQ.REG.SUPP = "F.CHEQUE.REGISTER.SUPPLEMENT"
     F.CHEQ.REG.SUPP = ""

     CALL OPF(FN.CHEQ.REG.SUPP,F.CHEQ.REG.SUPP)

     RETURN

 PROCESS:

     PAY.STOP.ID = ID.NEW
     DD.NO = R.NEW(AC.PAY.FIRST.CHEQUE.NO)
     CHEQ.REG.ID = "DD" : '.' : PAY.STOP.ID : '.' : DD.NO

     CALL F.READ(FN.CHEQ.REG.SUPP,CHEQ.REG.ID,R.CHEQ.REG.REC,F.CHEQ.REG.SUPP,R.ERR1)

     R.NEW(AC.PAY.CHEQUE.TYPE) = R.CHEQ.REG.REC<CC.CRS.ID.COMP1>    ; *default the cheque type value
     R.NEW(AC.PAY.AMOUNT.FROM) = R.CHEQ.REG.REC<CC.CRS.AMOUNT> ; *default the amount value
     R.NEW(AC.PAY.BENEFICIARY) = R.CHEQ.REG.REC<CC.CRS.PAYEE.NAME> ; *default the beneficiary value

     RETURN
 END
 