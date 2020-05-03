import java.sql.SQLOutput;

public class CalculateProbability {
    static Bayes bayes = new Bayes();

    public static void main(String[] args) throws Exception {
        bayes.read_Data("src/Data/data.txt");
        judge("no","divorced","120");
    }

    public static void judge(String refund,String maritalStatus,String taxableIncome){
        double refundNo = 0;
        double refundYes = 0;

        double taxableIncomeNo = 0;
        double taxableIncomeYes = 0;

        double ProabilityOfNo = 0;
        double ProabilityOfYes = 0;

        bayes.GetProbabilityNo();
        System.out.println(bayes.taxableIncomesNo);

        bayes.GetProbabilityYes();
        System.out.println(bayes.taxableIncomesYes);

        refundNo = bayes.Pro_OfVNRN;
        refundYes = bayes.Pro_OfVNRY;

        double avgNo = bayes.GetAvg(bayes.taxableIncomesNo);

        taxableIncomeNo = bayes.function(avgNo,
                                        bayes.GetVar(bayes.taxableIncomesNo,avgNo),
                                        Integer.parseInt(taxableIncome));

        double avgYes = bayes.GetAvg(bayes.taxableIncomesYes);

        taxableIncomeYes = bayes.function(avgYes,
                                        bayes.GetVar(bayes.taxableIncomesNo,avgYes),
                                        Integer.parseInt(taxableIncome));

        ProabilityOfNo = refundNo*Pro_OfMar(maritalStatus,false)*taxableIncomeNo;
        ProabilityOfYes = refundYes*Pro_OfMar(maritalStatus,true)*taxableIncomeYes ;

        System.out.println("P(x|no)"+ProabilityOfNo);
        System.out.println("P(x|yes)"+ProabilityOfYes);
        if(ProabilityOfYes>ProabilityOfNo){
            System.out.println("属于evade yes类");
        }else{
            System.out.println("属于evade no类");
        }
    }

    public static double Pro_OfMar(String maritalStatus,boolean NoYes){
        double maritalStatusNo = 0;
        double maritalStatusYes = 0;

        if(maritalStatus.equals("single")){
            if(NoYes)
                return maritalStatusYes = bayes.Pro_OfVYMS;
            else
                return maritalStatusNo = bayes.Pro_OfVNMS;

        }else if(maritalStatus.equals("married")){
            if(NoYes)
                return maritalStatusYes = bayes.Pro_OfVYMM;
            else
                return maritalStatusNo = bayes.Pro_OfVNMM;
        }else if(maritalStatus.equals("divorced")){
            if(NoYes)
                return maritalStatusYes = bayes.Pro_OfVYMD;
            else
                return maritalStatusNo = bayes.Pro_OfVNMD;
        }
        return 0;
    }

}

