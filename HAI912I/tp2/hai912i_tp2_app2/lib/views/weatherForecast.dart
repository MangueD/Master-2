import 'package:flutter/material.dart';
import 'package:hai912i_tp2_app2/views/weatherForecastSub.dart';
import 'package:intl/intl.dart';

Widget weatherForecast(
    List<String>? weatherList,
    List<double>? tempLowList,
    List<double>? tempHighList,
    List<double>? windList,
    List<int>? humidityList,
    ){

  return Center(
    child: Column(
      children: [
        Text("4-DAY WEATHER FORECAST"),
        SizedBox(
          height: 200,
          width: 500,
          child: ListView(
            scrollDirection: Axis.horizontal,
            children: [
              weatherForecastSub(
                  weatherList?[0],
                  tempLowList?[0],
                  tempHighList?[0],
                  windList?[0],
                  humidityList?[0],
                  DateFormat.EEEE().format(DateTime.now().add(Duration(days: 0))),
              ),

              SizedBox(width: 10,),

              weatherForecastSub(
                weatherList?[1],
                tempLowList?[1],
                tempHighList?[1],
                windList?[1],
                humidityList?[1],
                DateFormat.EEEE().format(DateTime.now().add(Duration(days: 1))),
              ),

              SizedBox(width: 10,),

              weatherForecastSub(
                weatherList?[2],
                tempLowList?[2],
                tempHighList?[2],
                windList?[2],
                humidityList?[2],
                DateFormat.EEEE().format(DateTime.now().add(Duration(days: 2))),
              ),

              SizedBox(width: 10,),

              weatherForecastSub(
                weatherList?[3],
                tempLowList?[3],
                tempHighList?[3],
                windList?[3],
                humidityList?[3],
                DateFormat.EEEE().format(DateTime.now().add(Duration(days: 3))),
              ),
            ],
          ),
        ),
      ]
    ),
  );
}