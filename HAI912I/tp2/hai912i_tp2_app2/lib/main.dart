import 'package:flutter/material.dart';
import 'package:hai912i_tp2_app2/models/weatherForecastModel.dart';
import 'package:hai912i_tp2_app2/models/weatherModel.dart';
import 'package:hai912i_tp2_app2/services/openWeatherAPI.dart';
import 'package:hai912i_tp2_app2/views/getWeatherIcon.dart';
import 'package:hai912i_tp2_app2/views/weather.dart';
import 'package:hai912i_tp2_app2/views/weatherDetails.dart';
import 'package:hai912i_tp2_app2/views/weatherForecast.dart';
import 'package:intl/intl.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: HomePage(),
    );
  }
}

class HomePage extends StatefulWidget{
  const HomePage({Key? key}) : super(key: key);

  @override
  HomePageState createState() => HomePageState();
}

class HomePageState extends State<HomePage>{
  Network api = Network();
  WeatherModel? model = WeatherModel();
  WeatherForecastModel? forecastModel = WeatherForecastModel();
  String city = "Montpellier";
  Future<void> getData() async{
    model = await api.getWeather(city: city);
    forecastModel = await api.getWeatherForecast(city: city);
  }
  TextEditingController controller = TextEditingController();

  @override
  void initState(){
    super.initState();
    api.getWeather(city: "Montpellier");
  }

  @override
  Widget build(BuildContext context){
    return FutureBuilder(
          future: getData(),
          builder: (context, snapshot){
            if(snapshot.connectionState == ConnectionState.done){
              return Scaffold(
                body: SafeArea(
                  child: Column(
                      crossAxisAlignment: CrossAxisAlignment.center,
                      children: [
                        SizedBox(height: 10,),

                        TextField(
                          controller: controller,
                          decoration: InputDecoration(
                            enabledBorder: OutlineInputBorder(
                              borderSide: BorderSide(width: 3, color: Colors.grey),
                              borderRadius: BorderRadius.circular(20.0),
                            ),
                              labelText: "Place",
                              suffixIcon: IconButton(
                                icon: Icon(Icons.search),
                                onPressed: () { setState(() {
                                  city = controller.text;
                                }); },
                              )
                          ),
                        ),

                        SizedBox(height: 15,),

                        weather("${model?.weather}",model?.temperature, "${model?.city}", DateFormat.yMMMMEEEEd().format(DateTime.now())),

                        SizedBox(height: 10),

                        Divider(),

                        weatherDetails(model?.wind, model?.humidity, model?.temperature),

                        SizedBox(height: 10,),

                        Divider(),

                        weatherForecast(
                            forecastModel!.weathers,
                            forecastModel!.temperaturesLow,
                            forecastModel!.temperaturesHigh,
                            forecastModel!.winds,
                            forecastModel!.humidities
                        )

                      ],


                    ),
                ),
              );
            }

            return Container();
          }
        );
  }
}

