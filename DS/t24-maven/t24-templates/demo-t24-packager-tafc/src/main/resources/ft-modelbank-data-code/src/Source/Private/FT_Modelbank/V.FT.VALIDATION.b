*-----------------------------------------------------------------------------
* <Rating>0</Rating>
*-----------------------------------------------------------------------------
SUBROUTINE V.FT.VALIDATION

    $INSERT I_COMMON
    $INSERT I_EQUATE
    $INSERT I_F.FUNDS.TRANSFER

    IF R.NEW(FT.DEBIT.AMOUNT) LT 5000 THEN
        E = "Amount Less than 5000 (DS packager demo)"
    END
END
