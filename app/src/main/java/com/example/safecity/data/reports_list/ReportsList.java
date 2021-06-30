package com.example.safecity.data.reports_list;

import java.util.ArrayList;
import java.util.Arrays;

public class ReportsList {
    public static ArrayList<Report> reports_list = new ArrayList<Report>(Arrays.asList(
            new Report("Edwin Yauyo", "Robo",
                    "Me robaron cerca a mi casa dos tipos en moto",false, "5h"),
            new Report("Edwin Yauyo", "Perdido",
                    "Fui al centro y no recuerdo como volver a casa",false, "5h"),
            new Report("Donald Kun", "Accidente",
                    "Choqué mi auto en la avenida Izaguirre",true, "5h")));
}