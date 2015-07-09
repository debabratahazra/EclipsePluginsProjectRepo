*-----------------------------------------------------------------------------
* <Rating>-41</Rating>
*-----------------------------------------------------------------------------
*
*-----------------------------------------------------------------------------
    SUBROUTINE E.MB.FT.GET.MESSAGE.TYPE(DATA.ARR)
*-----------------------------------------------------------------------------
* This routine will return the FT record and the corresponding delivery message
* type by reading the file DE.I.HEADER. In FT record only delivery reference information
* will be present. Record status and message type is got from the selection criteria and
* accordingly FT records are filtered and returned.
*-----------------------------------------------------------------------------
* Modification HIstory
* 21/10/80 - BG_100019949
*            Routine Standardisation
*
* 22/3/2010 - Replaced Select Statments by DAS
*----------------------------------------------------------------------------

    $INSERT I_COMMON
    $INSERT I_EQUATE
    $INSERT I_ENQUIRY.COMMON
    $INSERT I_F.FUNDS.TRANSFER
    $INSERT I_F.DE.HEADER
    $INSERT I_DAS.COMMON
    $INSERT I_DAS.FUNDS.TRANSFER

    GOSUB INITIALISE
    GOSUB PROCESS
    RETURN

INITIALISE:

    DATA.ARR = ''

    FN.FUNDS.TRANSFER.NAU = 'F.FUNDS.TRANSFER$NAU'
    F.FUNDS.TRANSFER.NAU = ''
    R.FUNDS.TRANSFER.REC = ''
    CALL OPF(FN.FUNDS.TRANSFER.NAU,F.FUNDS.TRANSFER.NAU)

    FN.DE.I.HEADER = 'F.DE.I.HEADER'
    F.DE.I.HEADER = ''
    R.DE.I.HEADER = ''
    CALL OPF(FN.DE.I.HEADER,F.DE.I.HEADER)
    RETURN

PROCESS:

    SELECTION.FIELDS = 'DELIVERY.INREF'
    SELECTION.OPERAND = 'NE'
    SELECTION.VALUES = ''

    THE.ARGS<1> = SELECTION.FIELDS
    THE.ARGS<2> = SELECTION.OPERAND
    THE.ARGS<3> = SELECTION.VALUES

    TABLE.NAME = 'FUNDS.TRANSFER'
    THE.LIST = 'dasFundsTransferInward'
    THE.ARGS = THE.ARGS
    TABLE.SUFFIX = '$NAU'

    CALL DAS(TABLE.NAME,THE.LIST,THE.ARGS,TABLE.SUFFIX)
    SEL.LIST = THE.LIST

    LOOP
        REMOVE FT.ID FROM SEL.LIST SETTING FT.ID.POS
    WHILE FT.ID:FT.ID.POS

        CALL F.READ(FN.FUNDS.TRANSFER.NAU,FT.ID,R.FUNDS.TRANSFER.REC,F.FUNDS.TRANSFER.NAU,READ.ERR)

        IN.DEL.REF.ID = R.FUNDS.TRANSFER.REC<FT.DELIVERY.INREF>
        FT.REC.STATUS = R.FUNDS.TRANSFER.REC<FT.RECORD.STATUS>

        CALL F.READ(FN.DE.I.HEADER,IN.DEL.REF.ID,R.DE.I.HEADER.REC,F.DE.I.HEADER,HEAD.ERR)

        CHECK.MSG.TYPE = R.DE.I.HEADER.REC<DE.HDR.MESSAGE.TYPE>

        LOCATE "MSG.TYPE" IN D.FIELDS<1> SETTING MSG.TYPE.POS THEN
            SEL.MSG.TYPE = D.RANGE.AND.VALUE<MSG.TYPE.POS>
        END
        LOCATE "REC.STATUS" IN D.FIELDS<1> SETTING MSG.TYPE.POS THEN
            SEL.REC.STATUS = D.RANGE.AND.VALUE<MSG.TYPE.POS>
        END

        GOSUB RETURN.ENQ.VAL

    REPEAT

    RETURN

****************
RETURN.ENQ.VAL:
****************

    IF CHECK.MSG.TYPE EQ SEL.MSG.TYPE AND FT.REC.STATUS EQ SEL.REC.STATUS THEN
        IF DATA.ARR THEN
            DATA.ARR := FM:FT.ID:"*":R.FUNDS.TRANSFER.REC<FT.TRANSACTION.TYPE>:"*":R.FUNDS.TRANSFER.REC<FT.DEBIT.ACCT.NO>:"*":R.FUNDS.TRANSFER.REC<FT.DEBIT.CURRENCY>:"*":R.FUNDS.TRANSFER.REC<FT.DEBIT.AMOUNT>:"*":R.FUNDS.TRANSFER.REC<FT.DELIVERY.INREF>:"*":R.FUNDS.TRANSFER.REC<FT.RECORD.STATUS>:"*":CHECK.MSG.TYPE
        END ELSE
            DATA.ARR = FT.ID:"*":R.FUNDS.TRANSFER.REC<FT.TRANSACTION.TYPE>:"*":R.FUNDS.TRANSFER.REC<FT.DEBIT.ACCT.NO>:"*":R.FUNDS.TRANSFER.REC<FT.DEBIT.CURRENCY>:"*":R.FUNDS.TRANSFER.REC<FT.DEBIT.AMOUNT>:"*":R.FUNDS.TRANSFER.REC<FT.DELIVERY.INREF>:"*":R.FUNDS.TRANSFER.REC<FT.RECORD.STATUS>:"*":CHECK.MSG.TYPE
        END
    END

    RETURN
