import 'dart:ffi';

import 'package:flutter/material.dart';
import 'package:hai912i_tp1_ex2/quizzprovider.dart';
import 'package:provider/provider.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        // une application utilisant Material Design
        title: 'My Second Flutter App',
        home: ChangeNotifierProvider(
          create: (_) => QuizzProvider(0, [
            Question(
                questionText: "1 + 1 = 3",
                isCorrect: false,
                imageRef: "assets/math.jpg"),
            Question(
                questionText:
                    "La France a gagné la coupe du monde de football 2022",
                isCorrect: false,
                imageRef: "assets/foot.jpg"),
            Question(
                questionText:
                    "Eliud Kipchoge est le premier marathonien à passer sous la barre des 2h00 pour un marathon",
                isCorrect: true,
                imageRef: "assets/marathon.jpg"),
          ]),
          child: QuizzPage(
            title: "Questions/Réponses",
          ),
        ));
  }
}

class QuizzPage extends StatelessWidget {
  QuizzPage({Key? key, required this.title}) : super(key: key);
  final String title;

  @override
  Widget build(BuildContext context) {
    QuizzProvider provider = Provider.of<QuizzProvider>(context);
    return Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.blueGrey,
          title: Center(child: Text(title)),
        ),
        backgroundColor: Colors.blueGrey,
        body: Container(
          child: Column(
            children: [
              Padding(
                padding: EdgeInsets.fromLTRB(60, 10, 60, 0),
                child: Container(
                  child: Image(
                    image: AssetImage(provider.quizz[provider.currQ].imageRef),
                  ),
                ),
              ),
              Padding(
                padding: EdgeInsets.fromLTRB(60, 40, 60, 40),
                child: Container(
                    padding: EdgeInsets.all(10),
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.all(Radius.circular(30)),
                      border: Border.all(color: Colors.grey),
                    ),
                    child: Text(
                      provider.quizz[provider.currQ].questionText,
                      style: const TextStyle(
                          fontStyle: FontStyle.italic, color: Colors.white),
                    )),
              ),
              Padding(
                padding: EdgeInsets.symmetric(horizontal: 10),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    ElevatedButton(
                      onPressed: () {
                        provider.checkAnswer(true, context);
                        provider.nextQuestion(context);
                      },
                      child: const Text('VRAI'),
                      style: ElevatedButton.styleFrom(primary: Colors.blueGrey),
                    ),
                    const SizedBox(
                      width: 40,
                    ),
                    ElevatedButton(
                      onPressed: () {
                        provider.checkAnswer(false, context);
                        provider.nextQuestion(context);
                      },
                      style: ElevatedButton.styleFrom(primary: Colors.blueGrey),
                      child: const Text('FAUX'),
                    ),
                    const SizedBox(
                      width: 40,
                    ),
                    ElevatedButton(
                      onPressed: () {
                        provider.nextQuestion(context);
                      },
                      style: ElevatedButton.styleFrom(primary: Colors.blueGrey),
                      child: const Icon(
                        Icons.arrow_forward_sharp,
                      ),
                    ),
                  ],
                ),
              )
            ],
          ),
        ));
  }
}

class Question {
  String questionText;
  String imageRef;
  bool isCorrect;
  Question(
      {required this.questionText,
      required this.isCorrect,
      required this.imageRef});
}
