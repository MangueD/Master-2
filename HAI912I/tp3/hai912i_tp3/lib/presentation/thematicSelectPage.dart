import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'package:hai912i_tp3/data/databaseQuery.dart';
import 'package:hai912i_tp3/presentation/quizzPage.dart';
import 'package:provider/provider.dart';

import '../logic/quizzprovider.dart';
import 'addQuestionPage.dart';

class ThematicSelect extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    QuizzQuery query = QuizzQuery().getInstance();
    return FutureBuilder(
        future: query.queryThematics(),
        builder: (BuildContext context, AsyncSnapshot<dynamic> snapshot) {
          List<String> thematics = query.thematics;
          List<Widget> thematicButtons = [];

          for (String t in thematics) {
            thematicButtons.add(
              ElevatedButton(
                onPressed: () {
                  Navigator.pushReplacement(
                      context,
                      MaterialPageRoute(
                        builder: (context) => FutureBuilder(
                            future: Firebase.initializeApp(),
                            builder: (BuildContext context,
                                AsyncSnapshot<dynamic> snapshot) {
                              return FutureBuilder(
                                  future: query.queryQuestions(t),
                                  builder: (BuildContext context,
                                      AsyncSnapshot<dynamic> snapshot) {
                                    QuizzQuery q = QuizzQuery().getInstance();
                                    q.queryQuestions(t);
                                    return ChangeNotifierProvider(
                                      create: (_) => QuizzProvider(q.quizz),
                                      child: QuizzPage(
                                        title: "Questions/Réponses",
                                      ),
                                    );
                                  });
                            }),
                      ));
                },
                style: ElevatedButton.styleFrom(primary: Colors.blueGrey),
                child: Text(t),
              ),
            );
          }
          return Scaffold(
            appBar: AppBar(
              backgroundColor: Colors.blueGrey,
              title: Center(child: Text("Menu Principal")),
            ),
            backgroundColor: Colors.blueGrey,
            body: Center(
              child:
                  Column(mainAxisAlignment: MainAxisAlignment.start, children: [
                const Text(
                  "Choix du thème",
                  style: TextStyle(
                    color: Colors.white,
                    fontSize: 30,
                  ),
                ),
                SizedBox(
                  height: 10,
                ),
                SizedBox(
                  height: 100,
                  width: 100,
                  child: ListView(
                    scrollDirection: Axis.vertical,
                    children: thematicButtons,
                  ),
                ),
                SizedBox(
                  height: 10,
                ),
                Divider(),
                ElevatedButton(
                    onPressed: () {
                      Navigator.push(
                          context,
                          MaterialPageRoute(
                              builder: (context) => AddingQuestion()));
                    },
                    child: Text("Ajouter une question"))
              ]),
            ),
          );
        });
  }
}
