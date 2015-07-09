*-----------------------------------------------------------------------------
* <Rating>-41</Rating>
*-----------------------------------------------------------------------------
*This routine fetches the records in FT$NAU, groups them based on the debit
*amount and the count and debit amount is returned

    SUBROUTINE E.MB.GET.FT.LIST(FT.LIST)

*-----------------------------------------------------------------------------
*
* 22/03/2010 - Replaced Select Statements by DAS
*
* 30/03/2012 - Task : 381329 / Ref Defect : 381271
*              Quotes has been removed in dasFundsTransferInward
*-----------------------------------------------------------------------------
    $INSERT I_COMMON
    $INSERT I_EQUATE
    $INSERT I_ENQUIRY.COMMON
    $INSERT I_F.FUNDS.TRANSFER
    $INSERT I_DAS.FUNDS.TRANSFER

    GOSUB INIT
    GOSUB PROCESS

    RETURN

INIT:

    FN.FT.TFR = 'FBNK.FUNDS.TRANSFER$NAU'
    F.FT.TFR = ''
    CALL OPF(FN.FT.TFR,F.FT.TFR)
    DEBIT.AMOUNT = 0
    NO.ONE.THN = 0
    NO.FIVE.THN = 0
    NO.TEN.THN = 0
    NO.TWENTYFIVE.THN = 0
    NO.FIFTY.THN = 0
    NO.ONE.LAKH = 0
    NO.GT.ONE.LAKH = 0

    RETURN

PROCESS:

    Y.FIELDS.CNT = ''
    Y.OPERAND = ''
    Y.RANGE.AND.VALUE = ''
    Y.FIELDS.CNT = DCOUNT(D.FIELDS,FM)

    FOR Y.COUNT = 1 TO Y.FIELDS.CNT

        IF D.FIELDS<Y.COUNT> EQ 'TRANSACTION.TYPE' THEN
            Y.OPERAND = D.LOGICAL.OPERANDS<Y.COUNT>
            Y.RANGE.AND.VALUE = D.RANGE.AND.VALUE<Y.COUNT>

            Y.SEL.OPERAND = OPERAND.LIST<Y.OPERAND>

            IF Y.SEL.OPERAND EQ 'LK' THEN
                Y.SEL.OPERAND = 'LIKE'
            END

            Y.STR1 = SM
            Y.STR2 = ' '

            CHANGE Y.STR1 TO Y.STR2 IN Y.RANGE.AND.VALUE

            TRANSACTION.FIELDS<1,-1> = "TRANSACTION.TYPE "
            TRANSACTION.OPERANDS<1,-1> =  Y.SEL.OPERAND
            TRANSACTION.RANGE<1,-1> =  Y.RANGE.AND.VALUE

        END

    NEXT Y.COUNT

    SELECTION.FIELDS = 'RECORD.STATUS':@VM:TRANSACTION.FIELDS
    SELECTION.OPERAND = 'EQ':@VM:TRANSACTION.OPERANDS
    SELECTION.VALUES = 'INAU':@VM:TRANSACTION.RANGE
    SELECTION.JOINS = 'AND':@VM:'AND'

    THE.ARGS<1> = SELECTION.FIELDS
    THE.ARGS<2> = SELECTION.OPERAND
    THE.ARGS<3> = SELECTION.VALUES
    THE.ARGS<4> = SELECTION.JOINS

    THE.LIST = dasFundsTransferInward
    CALL DAS("FUNDS.TRANSFER",THE.LIST,THE.ARGS,"$NAU")

    SEL.LIST = THE.LIST

    LOOP

        REMOVE FT.ID FROM SEL.LIST SETTING POS
    WHILE FT.ID:POS
        FT.ERR = ''
        CALL F.READ(FN.FT.TFR,FT.ID,R.FT.TFR,F.FT.TFR,FT.ERR)
        DEBIT.AMOUNT = R.FT.TFR<FT.LOC.AMT.CREDITED>
        GOSUB PROCESS.CREDIT.AMOUNT
    REPEAT
    FT.LIST<1,-1> = '>100,000*':NO.GT.ONE.LAKH
    FT.LIST<1,-1> = '100,000*':NO.ONE.LAKH
    FT.LIST<1,-1> = '50,000*':NO.FIFTY.THN
    FT.LIST<1,-1> = '25,000*':NO.TWENTYFIVE.THN
    FT.LIST<1,-1> = '10,000*':NO.TEN.THN
    FT.LIST<1,-1> = '5000*':NO.FIVE.THN
    FT.LIST<1,-1> = '1000*':NO.ONE.THN
    CONVERT @VM TO @FM IN FT.LIST
    RETURN

PROCESS.CREDIT.AMOUNT:

    BEGIN CASE
    CASE DEBIT.AMOUNT <= 1000
        NO.ONE.THN = NO.ONE.THN + 1
    CASE DEBIT.AMOUNT > 1000 AND DEBIT.AMOUNT <= 5000
        NO.FIVE.THN = NO.FIVE.THN + 1
    CASE DEBIT.AMOUNT > 5000 AND DEBIT.AMOUNT <= 10000
        NO.TEN.THN = NO.TEN.THN + 1
    CASE DEBIT.AMOUNT > 10000 AND DEBIT.AMOUNT <= 25000
        NO.TWENTYFIVE.THN = NO.TWENTYFIVE.THN + 1
    CASE DEBIT.AMOUNT > 25000 AND DEBIT.AMOUNT <= 50000
        NO.FIFTY.THN = NO.FIFTY.THN + 1
    CASE DEBIT.AMOUNT > 50000 AND DEBIT.AMOUNT <= 100000
        NO.ONE.LAKH = NO.ONE.LAKH + 1
    CASE DEBIT.AMOUNT > 100000
        NO.GT.ONE.LAKH = NO.GT.ONE.LAKH + 1
    END CASE

    RETURN
