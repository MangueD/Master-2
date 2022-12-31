import "package:flutter/material.dart";
import 'package:font_awesome_flutter/font_awesome_flutter.dart';

Widget weatherDetails(double? wind, int? humidity, double? temperature){
  return Container(
    width: double.infinity,
    padding:  EdgeInsets.all(18),
    child: Column(
      mainAxisAlignment: MainAxisAlignment.center,
      crossAxisAlignment: CrossAxisAlignment.center,
      children: [
        Row(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            Column(
              mainAxisAlignment: MainAxisAlignment.center,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                Text(
                    "${wind?.toStringAsFixed(1)} Km/h"
                ),

                SizedBox(height: 10),

                Icon(FontAwesomeIcons.wind, color: Colors.brown,)
              ],
            ),

            SizedBox(width: 10,),

            Column(
              mainAxisAlignment: MainAxisAlignment.center,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                Text(
                    "$humidity %"
                ),

                SizedBox(height: 10),

                Icon(FontAwesomeIcons.droplet, color: Colors.brown,)
              ],
            ),

            SizedBox(width: 10,),

            Column(
              mainAxisAlignment: MainAxisAlignment.center,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                Text(
                    "${temperature?.toStringAsFixed(1)}°C"
                ),

                SizedBox(height: 10),

                Icon(FontAwesomeIcons.temperatureHalf, color: Colors.brown,)
              ],
            ),


          ],
        )
      ],
    ),
  );
}