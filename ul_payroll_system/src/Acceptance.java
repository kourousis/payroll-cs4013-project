public class Acceptance extends Promotion{

    public void PromotionPending() {
        if (PendingState && !AcceptanceState){
            System.out.println("You have a Promotion Request");
        }
        else if (PendingState && AcceptanceState){
            System.out.println("You have been promoted ");
        }
    }
}
