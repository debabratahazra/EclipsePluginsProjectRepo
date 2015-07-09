*-----------------------------------------------------------------------------
* <Rating>0</Rating>
*-----------------------------------------------------------------------------
* Subroutine type : SUBROUTINE
* Attached to     : VERSION record FUNDS.TRANSFER,AI.AC.CONFIRM
* Attached as     : validation rtn
*-----------------------------------------------------------------------------
    SUBROUTINE V.MB.CLEAR.PROCESS.DATE
    $INSERT I_COMMON
    $INSERT I_EQUATE
    $INSERT I_F.FUNDS.TRANSFER
*
*Defaulting processing date,debut/credit value date to today ,if the date is less than
*today. so that it wont cause any problem if user input an transaction while date change during cob.
*


    IF R.NEW(FT.PROCESSING.DATE) LT TODAY THEN
        R.NEW(FT.PROCESSING.DATE) = TODAY
        IF R.NEW(FT.DEBIT.VALUE.DATE) LT TODAY THEN
            R.NEW(FT.DEBIT.VALUE.DATE) = TODAY
        END
        IF R.NEW(FT.CREDIT.VALUE.DATE) LT TODAY THEN
            R.NEW(FT.CREDIT.VALUE.DATE) = TODAY
        END
    END

    RETURN
END
