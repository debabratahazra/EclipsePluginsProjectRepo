*-----------------------------------------------------------------------------
* <Rating>-23</Rating>
*-----------------------------------------------------------------------------
    SUBROUTINE E.MB.STO.LIST(ENQ.DATA)
*-----------------------------------------------------------------------------
* this is a build routine that is attached to AI.STO to build the list of
* standing order ids

    $INSERT I_COMMON
    $INSERT I_EQUATE
    $INSERT I_ENQUIRY.COMMON
    $INSERT I_EB.EXTERNAL.COMMON
    $INSERT I_F.STANDING.ORDER
    $INSERT I_System
    $INSERT I_DAS.STANDING.ORDER
    $INSERT I_EB.EXTERNAL.COMMON

    GOSUB INIT
    GOSUB PROCESS
    RETURN

*-----------------------------------------------------------------------------
INIT:
*-----------------------------------------------------------------------------
*Initialise the variables and open the required files.
    FN.STO = "F.STANDING.ORDER"
    F.STO = " "
    CALL OPF(FN.STO,F.STO)

    SMS.ACCOUNTS = System.getVariable('EXT.SMS.ACCOUNTS')
    CUST.ID = EB.EXTERNAL$CUSTOMER

    RETURN

*-----------------------------------------------------------------------------
PROCESS:
*-----------------------------------------------------------------------------
* fetch the standing order ids
    CONVERT SM TO FM IN SMS.ACCOUNTS


    LOOP
        REMOVE ACCT.ID FROM SMS.ACCOUNTS SETTING ACC.POS
    WHILE ACCT.ID : ACC.POS
        THE.PRE.ARGS = ACCT.ID:@FM:CUST.ID
        THE.PRE.LIST = DAS.STANDING.ORDER$ACCT.NO
        CALL DAS("STANDING.ORDER",THE.PRE.LIST,THE.PRE.ARGS,'')
        SEL.LIST = THE.PRE.LIST

        IF SEL.LIST NE '' THEN
            ACCT.ARR<-1>= SEL.LIST
        END

    REPEAT

    CONVERT FM TO ' ' IN ACCT.ARR
    ENQ.DATA<2> = '@ID'
    ENQ.DATA<3> = 'EQ'
    ENQ.DATA<4> = ACCT.ARR

    RETURN
END
*-----------------------------------------------------------------------------
