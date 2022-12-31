

class WeatherForecastModel{
  static final  FORECAST_DAYS = 4;
  List<double>? temperaturesLow = [];
  List<double>? temperaturesHigh = [];
  List<double>? winds = [];
  List<int>? humidities = [];
  List<String>? weathers = [];
  static int funcount = 0;

  WeatherForecastModel({
    this.temperaturesLow,
    this.temperaturesHigh,
    this.winds,
    this.humidities,
    this.weathers,
  });

  WeatherForecastModel.fromJson(Map<String, dynamic> json){
    for(int i = 0; i < FORECAST_DAYS; i++){
      double temperatureLow = 1000;
      double temperatureHigh = -300;
      num avgHumidity = 0;
      double avgWind = 0;

      for(int j = 0; j < 8; j++){

        if(temperatureLow > json["list"][(i * 8) + j]["main"]["temp_min"]){
          temperatureLow = json["list"][(i * 8) + j]["main"]["temp_min"].toDouble();
        }

        if(temperatureHigh < json["list"][(i * 8) + j]["main"]["temp_max"]){
          temperatureHigh = json["list"][(i * 8) + j]["main"]["temp_max"].toDouble();
        }

        avgHumidity += json["list"][(i * 8) + j]["main"]["humidity"];
        avgWind += json["list"][(i * 8) + j]["wind"]["speed"];

      }

      weathers?.add(json["list"][i * 8]["weather"][0]["main"]);
      winds?.add(avgWind / 8);
      humidities?.add((avgHumidity / 8).round());
      temperaturesHigh?.add(temperatureHigh);
      temperaturesLow?.add(temperatureLow);
    }

    print("$weathers + $temperaturesLow $temperaturesHigh $winds $humidities");

  }
}