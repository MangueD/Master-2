import 'dart:ffi';

import 'package:flutter/material.dart';
import 'package:hai912i_tp1_ex2/results.dart';

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
      home: QuizzPage(),
    );
  }
}

class QuizzPage extends StatefulWidget {
  List<Question> quizz = [
    Question(
        questionText: "1 + 1 = 3",
        isCorrect: false,
        imageRef: "assets/math.jpg"),
    Question(
        questionText: "La France a gagné la coupe du monde de football 2022",
        isCorrect: false,
        imageRef: "assets/foot.jpg"),
    Question(
        questionText:
            "Eliud Kipchoge est le premier marathonien à passer sous la barre des 2h00 pour un marathon",
        isCorrect: true,
        imageRef: "assets/marathon.jpg"),
  ];

  @override
  QuizzPageState createState() =>
      QuizzPageState(title: "Questions/Réponses", quizz: quizz);
}

class QuizzPageState extends State<QuizzPage> {
  QuizzPageState({Key? key, required this.title, required this.quizz});
  final String title;

  final List<Question> quizz;

  int currQ = 0;
  int score = 0;

  @override
  Widget build(BuildContext context) {
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
                    image: AssetImage(quizz.elementAt(currQ).imageRef),
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
                      quizz.elementAt(currQ).questionText,
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
                        setState(() {
                          score = _checkAnswer(true, context) ? 1 : 0;
                          _nextQuestion();
                        });
                      },
                      child: const Text('VRAI'),
                      style: ElevatedButton.styleFrom(primary: Colors.blueGrey),
                    ),
                    const SizedBox(
                      width: 40,
                    ),
                    ElevatedButton(
                      onPressed: () {
                        setState(() {
                          score = _checkAnswer(true, context) ? 1 : 0;
                          _nextQuestion();
                        });
                      },
                      style: ElevatedButton.styleFrom(primary: Colors.blueGrey),
                      child: const Text('FAUX'),
                    ),
                    const SizedBox(
                      width: 40,
                    ),
                    ElevatedButton(
                      onPressed: () {
                        setState(() {
                          _nextQuestion();
                        });
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

  bool _checkAnswer(bool userChoice, BuildContext context) {
    if (userChoice == quizz.elementAt(currQ).isCorrect) {
      return true;
    }
    return false;
  }

  void _nextQuestion() {
    int size = quizz.length;
    if (currQ < size - 1) {
      currQ++;
    } else {
      Navigator.pushReplacement(
          context,
          MaterialPageRoute(
              builder: (context) => Results(score, quizz.length)));
    }
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
