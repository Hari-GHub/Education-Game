package au.edu.jcu.cp3406.edugame;

public class QuizDatabase {
}

class questionsLibrary {
    private final String[] QuestionsList =
            {
                    "What toy or sports item was once used as a hunting weapon by the Aborigines?",
                    "Which Australian city hosted the olympics in 1956?",
                    "Which kind of people were sent as the infamous first settlers of Australia after colonisation?",
                    "What kind of people were vastly affected by the 'Stolen Generation'?",
                    "Which Australian city was originally founded as the penal colony?",
                    "What was the Eureka Stockade all about?",
                    "What does 'Anzac' in Anzac day stands for?",
                    "What important mineral was unearthed in Victoria and NSW during mid-1800s?",
                    "Which famous captain discovered and claimed part of Australia for the UK?",
                    "Who became the first female prime minister in Australia's history?",
                    "When did Australia unite to become one united nation with recognized constitution?"
            };
    private final String[][] choice = {
            {"Didgeridoo","Boomerang","Kulintang"},
            {"Melbourne","Adelaide","Canberra"},
            {"Doctors","Teachers","Convicts"},
            {"Women","Elderly people","Children"},
            {"Sydney","Townsville","Perth"},
            {"Stock trade revolution","A rebellion of gold miners","Labourers March"},
            {"Australia and New Zealand Army Corps","Air and Navy Zeals Army Corp ","Australia and New Zealand Allied Corps"},
            {"Gold","Coal","Sedimentary"},
            {"Captain Jack Sparrow","Captain James Cook","Captain Curt Cameroon"},
            {"Julia Gillard","Rakhi Ranjan","Valentina Tereshkova"},
            {"1701","1801","1901"}


    };

    private final String[] correctAnswers ={"Boomerang","Melbourne","Convicts","Children","Sydney","A rebellion of gold miners",
            "Australia and New Zealand Army Corps","Gold","Captain James Cook","Julia Gillard","1901"};

    String getques(int a){
        return QuestionsList[a];
    }

    String getch1(int a){
        return choice[a][0];
    }

    String getch2(int a){
        return choice[a][1];
    }

    String getch3(int a){
        return choice[a][2];
    }

    String getcorrAn(int a)
    {
        return correctAnswers[a];
    }
}
