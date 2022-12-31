import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:hai912i_tp2_app2/views/getWeatherIcon.dart';

Widget weather(String? weatherDescription, double? temperature, String? location, String? date, ){
  return Center(
    child: Column(
      crossAxisAlignment: CrossAxisAlignment.center,
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        Text(
            "$location",
          style: TextStyle(
            fontSize: 20,
            fontWeight: FontWeight.bold,
          ),
        ),


        Text(
            "$date"
        ),

        SizedBox(height: 15,),

        getWeatherIcon(weatherDescription: weatherDescription, color: Colors.pinkAccent, size: 200),

        SizedBox(height: 10),

        Row(
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(
              "${temperature?.toStringAsFixed(1)}°C",
              style: TextStyle(
                fontSize: 30
              ),
            ),

            SizedBox(width: 10,),

            Text(
              "${weatherDescription?.toUpperCase()}"
            )
          ],
        ),

      ],
    ),
  );
}