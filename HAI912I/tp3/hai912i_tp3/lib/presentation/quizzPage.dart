
import 'package:flutter/material.dart';
import 'package:hai912i_tp3/data/databaseQuery.dart';
import 'package:provider/provider.dart';

import '../logic/question.dart';
import '../logic/quizzprovider.dart';

class QuizzPage extends StatelessWidget {
  QuizzPage({Key? key, required this.title}) : super(key: key);
  final String title;


  @override
  Widget build(BuildContext context) {
    QuizzProvider provider = Provider.of<QuizzProvider>(context);
    provider.setQuizz(QuizzQuery().getInstance().quizz);
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
                          image: NetworkImage(
                              provider.quizz[provider.currQ].imageRef),
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
                                fontStyle: FontStyle.italic,
                                color: Colors.white
                            ),
                          )
                      ),
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
                            style: ElevatedButton.styleFrom(
                                primary: Colors.blueGrey
                            ),
                          ),


                          const SizedBox(
                            width: 40,
                          ),

                          ElevatedButton(
                            onPressed: () {
                              provider.checkAnswer(false, context);
                              provider.nextQuestion(context);
                            },
                            style: ElevatedButton.styleFrom(
                                primary: Colors.blueGrey
                            ),
                            child: const Text('FAUX'),
                          ),

                          const SizedBox(
                            width: 40,
                          ),

                          ElevatedButton(
                            onPressed: () {
                              provider.nextQuestion(context);
                            },
                            style: ElevatedButton.styleFrom(
                                primary: Colors.blueGrey
                            ),
                            child: const Icon(
                              Icons.arrow_forward_sharp,
                            ),
                          ),
                        ],
                      ),
                    )
                  ],
                ),
              )
          );
  }

}