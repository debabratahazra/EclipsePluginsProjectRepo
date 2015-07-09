*-----------------------------------------------------------------------------
* <Rating>0</Rating>
*-----------------------------------------------------------------------------
* This subroutine is attached to the enquiries (conversion field) to pass the frequency dates
* and return the text according to that frequency.
*
*===============================================================================================
*                            M O D I F I C A T I O N S
*
*   14/02/2009    -- First Version **Naresh**
*
* 08/04/2011  - DEFECT 173928 / TASK 187885
*             - Frequency is displaying in unreadable format while running the enquiry
*               CUSTOMER.SWEEP.SCV
*=================================================================================================

    SUBROUTINE E.MB.FREQ.TEXT

    $INSERT I_COMMON
    $INSERT I_EQUATE
    $INSERT I_ENQUIRY.COMMON

    IF O.DATA MATCHES "8N5X":VM:"5X" THEN
        IN.FREQ = O.DATA[5]
    END ELSE
        IN.FREQ = FIELD(O.DATA," ",2,4)
    END

    CALL EB.BUILD.RECURRENCE.MASK(IN.FREQ,"",OUT.FREQ)

    O.DATA = OUT.FREQ

    RETURN
