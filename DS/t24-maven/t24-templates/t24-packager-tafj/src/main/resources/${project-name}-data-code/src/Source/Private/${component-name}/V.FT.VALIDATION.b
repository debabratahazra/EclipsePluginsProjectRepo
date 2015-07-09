*-----------------------------------------------------------------------------
* <Rating>0</Rating>
*-----------------------------------------------------------------------------
SUBROUTINE V.FT.VALIDATION

    $INSERT I_COMMON
    $INSERT I_EQUATE
    $INSERT I_F.FUNDS.TRANSFER

    IF R.NEW(FT.CREDIT.AMOUNT) GT 500 THEN
        E = "Amount greater than 500 (DS packager demo)"
    END
END
