*-----------------------------------------------------------------------------
* <Rating>-23</Rating>
*-----------------------------------------------------------------------------
****************************************************************************************************
* Id routine for Payment stop version
****************************************************************************************************
    SUBROUTINE V.MB.PAY.STOP.UNAUTH.CHECK 
****************************************************************************************************
* 29/02/2012- New Development
* Purpose - The routine to validate @ID field of PAYMENT.STOP,DRAFT.LCY version and generate error when unauth 
*           payment stop record  with same id is edited.
* Developed By - Rekha M
****************************************************************************************************
* 03/04/2012 - Task : 383304 / Ref Defect : 383159
*              Error Id has been modified as 'FT-PS.REC.IN.UNAUTH.STATUS'
*--------------------------------------------------------------------------------------------------
    $INSERT I_COMMON
    $INSERT I_EQUATE
    $INSERT I_F.PAYMENT.STOP


    GOSUB INIT
    GOSUB PROCESS

    RETURN

INIT:

    F.PAY.STOP = ''
    FN.PAY.STOP = "F.PAYMENT.STOP$NAU"
    CALL OPF(FN.PAY.STOP,F.PAY.STOP)

    RETURN

PROCESS:

     IF V$FUNCTION MATCHES 'I' THEN      ;*validation only for input function

         Y.PS.ID = COMI     ;* Get the record id

         CALL F.READ(FN.PAY.STOP,Y.PS.ID,R.PS.REC,F.PAY.STOP,ERR)
         IF R.PS.REC<AC.PAY.RECORD.STATUS> EQ 'INAU' THEN       ;* record found in Unauth status
             E = "FT-PS.REC.IN.UNAUTH.STATUS"
             RETURN
         END
     END
     RETURN
 END
