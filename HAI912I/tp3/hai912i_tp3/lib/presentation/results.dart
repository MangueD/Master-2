import 'package:flutter/material.dart';

import '../main.dart';

class Results extends StatelessWidget{

  Results(this.score, this.quizzSize);

  int score = 0;
  int quizzSize = 0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.blueGrey,
          title: Center(child: Text("Résulats")),
        ),
        backgroundColor: Colors.blueGrey,
        body: Container(
          alignment: Alignment.center,
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Padding(
                padding: EdgeInsets.fromLTRB(60, 40, 60 , 40),
                child: Container(
                    padding: EdgeInsets.all(10),
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.all(Radius.circular(30)),
                      border: Border.all(color: Colors.grey),
                    ),
                    child: Text(
                      "Score finale : ${score}/${quizzSize}",
                      style: const TextStyle(
                          fontSize: 30,
                          fontStyle: FontStyle.italic,
                          color: Colors.white
                      ),
                    )
                ),
              ),

              Padding(
                padding: EdgeInsets.symmetric(horizontal: 10),
                child:
                    ElevatedButton(
                      onPressed: (){
                        Navigator.pushReplacement(context,
                            MaterialPageRoute(builder: (context) => const MyApp()));
                      },
                      child: const Text('Rejouer'),
                      style: ElevatedButton.styleFrom(
                          primary: Colors.blueGrey
                      ),
                    ),

              )
            ],
          ),
        )
    );
  }
}