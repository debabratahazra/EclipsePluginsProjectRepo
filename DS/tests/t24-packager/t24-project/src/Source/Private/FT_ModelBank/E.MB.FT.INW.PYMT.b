*-----------------------------------------------------------------------------
* <Rating>-72</Rating>
*-----------------------------------------------------------------------------
*This routine fetches records from FUNDS.TRASNFER$NAU based on certain conditions
*and returns the error record count for 103,200,202 messages.

    SUBROUTINE E.MB.FT.INW.PYMT(FT.REC)
*-----------------------------------------------------------------------------
*
* 22/3/2010 - Replaced Select Statments by DAS
*
* 30/03/2012 - Task : 381329 / Ref Defect : 381271
*              Quotes has been removed in dasFundsTransferInward
*
*-----------------------------------------------------------------------------

    $INSERT I_COMMON
    $INSERT I_EQUATE
    $INSERT I_ENQUIRY.COMMON
    $INSERT I_F.FUNDS.TRANSFER
    $INSERT I_DAS.COMMON
    $INSERT I_DAS.FUNDS.TRANSFER

    GOSUB INIT
    GOSUB PROCESS
    RETURN

INIT:

    FN.FT.TFR = 'F.FUNDS.TRANSFER$NAU'
    F.FT.TFR = ''
    CALL OPF(FN.FT.TFR,F.FT.TFR)
    MT103.UNAUTH = 0
    MT103.ERRORS = 0
    MT200.UNAUTH = 0
    MT200.ERRORS = 0
    MT202.UNAUTH = 0
    MT202.ERRORS = 0
    RETURN

PROCESS:

    TABLE.NAME = 'FUNDS.TRANSFER'
    THE.LIST = dasFundsTransferInward
    TABLE.SUFFIX = '$NAU'

    SELECTION.FIELDS = '(INWARD.PAY.TYPE':@VM:'INWARD.PAY.TYPE':@VM:'INWARD.PAY.TYPE':@VM:'(RECORD.STATUS':@VM:'RECORD.STATUS'
    SELECTION.OPERAND = 'EQ':@VM:'EQ':@VM:'EQ':@VM:'EQ':@VM:'EQ'
    SELECTION.VALUES = 'MT103':@VM:'MT200':@VM:'MT202)':@VM:'IHLD':@VM:'INAU)'
    SELECTION.JOINS = 'OR':@VM:'OR':@VM:'AND':@VM:'OR'

    THE.ARGS<1> = SELECTION.FIELDS
    THE.ARGS<2> = SELECTION.OPERAND
    THE.ARGS<3> = SELECTION.VALUES
    THE.ARGS<4> = SELECTION.JOINS

    CALL DAS(TABLE.NAME,THE.LIST,THE.ARGS,TABLE.SUFFIX)
    SEL.LIST = THE.LIST

    LOOP
        REMOVE FT.ID FROM SEL.LIST SETTING POS
    WHILE FT.ID:POS
        FT.ERR = ''
        CALL F.READ(FN.FT.TFR,FT.ID,R.FT.TFR,F.FT.TFR,FT.ERR)
        PAY.TYPE = R.FT.TFR<FT.INWARD.PAY.TYPE>
        GOSUB PROCESS.PAYMENT.TYPE
    REPEAT
    FT.REC<1,-1> = '103 Unauth*':MT103.UNAUTH
    FT.REC<1,-1> = '103 Errors*':MT103.ERRORS
    FT.REC<1,-1> = '200 Unauth*':MT200.UNAUTH
    FT.REC<1,-1> = '200 Errors*':MT200.ERRORS
    FT.REC<1,-1> = '202 Unauth*':MT202.UNAUTH
    FT.REC<1,-1> = '202 Errors*':MT202.ERRORS
    CONVERT @VM TO @FM IN FT.REC
    RETURN

PROCESS.PAYMENT.TYPE:

    RECORD.STATUS = R.FT.TFR<FT.RECORD.STATUS>
    BEGIN CASE
    CASE PAY.TYPE EQ 'MT103' AND RECORD.STATUS EQ 'IHLD'
        MT103.ERRORS = MT103.ERRORS + 1
    CASE PAY.TYPE EQ 'MT103' AND RECORD.STATUS EQ 'INAU'
        MT103.UNAUTH = MT103.UNAUTH + 1
    CASE PAY.TYPE EQ 'MT200' AND RECORD.STATUS EQ 'IHLD'
        MT200.ERRORS = MT200.ERRORS + 1
    CASE PAY.TYPE EQ 'MT200' AND RECORD.STATUS EQ 'INAU'
        MT200.UNAUTH = MT200.UNAUTH + 1
    CASE PAY.TYPE EQ 'MT202' AND RECORD.STATUS EQ 'IHLD'
        MT202.ERRORS = MT202.ERRORS + 1
    CASE PAY.TYPE EQ 'MT202' AND RECORD.STATUS EQ 'INAU'
        MT202.UNAUTH = MT202.UNAUTH + 1
    END CASE

    RETURN
