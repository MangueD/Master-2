import 'package:flutter/material.dart';

void main() => runApp(MyApp()); // point d'entrée


// Le widget racine de notre application
class MyApp extends StatelessWidget {
    const MyApp({Key? key}) : super(key: key);

    @override
    Widget build(BuildContext context) {
        return MaterialApp( // une application utilisant Material Design
            title: "Profile",
            home: const ProfileHomePage(),
        );
    }
}

class ProfileHomePage extends StatelessWidget {
    const ProfileHomePage({Key? key}) : super(key: key);

    @override
    Widget build(BuildContext context) {
        return Scaffold(
            appBar: AppBar(
                title: Text("Profile Home Page"),
                centerTitle: false,
            ),

            body: Container(
                alignment: Alignment.center,
                child: Stack(
                    children: <Widget>[
                        Padding(
                            padding: EdgeInsets.symmetric(horizontal: 60, vertical: 250),
                            child : _getCard()
                        ),
                        Padding(
                            padding: EdgeInsets.fromLTRB(126.8, 180, 126.8, 406),
                            child: _getAvatar()
                        ),
                    ],
                ),
            ),

        );
    }
    Container _getCard() {
        return Container(
            alignment: Alignment.center,
            decoration: BoxDecoration(
                borderRadius: BorderRadius.circular(20),
                color: Colors.orange,
            ),

            child: Padding(
                padding: EdgeInsets.fromLTRB(0, 100, 0, 20),
                child: Container(
                    alignment: Alignment.center,
                    child:Column(
                        children: const <Widget>[
                            Text(
                                "Sangdi Brazard",
                                style: TextStyle(
                                    color: Colors.white,
                                    fontSize: 18,
                                    fontStyle: FontStyle.italic
                                ),
                            ),
                            Text(
                                "sb@etu.umontpellier.fr",
                                style: TextStyle(
                                    color: Colors.white,
                                    fontSize: 18,
                                    fontStyle: FontStyle.italic
                                ),
                            ),
                            Text(
                                "Twitter: xxxx",
                                style: TextStyle(
                                    color: Colors.white,
                                    fontSize: 18,
                                    fontStyle: FontStyle.italic
                                ),
                            ),
                        ],)
            ))

        );
    }
    Container _getAvatar() {
        return Container(
            alignment: Alignment.topCenter,
            decoration: BoxDecoration(
                borderRadius: BorderRadius.circular(100),
                color: Colors.orange,
            ),
            child: Padding(
                padding: EdgeInsets.symmetric(horizontal: 1, vertical: 2),
                child: Container(
                    alignment: Alignment.center,
                    child: const CircleAvatar(
                        backgroundImage: AssetImage("assets/steak.jpg"),
                        radius: 100.0,
                    ),
                ),
            )
        );
    }
}
