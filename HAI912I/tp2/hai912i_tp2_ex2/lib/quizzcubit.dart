import 'package:bloc/bloc.dart';
import 'package:flutter/material.dart';
import 'package:hai912i_tp1_ex2/results.dart';

import 'main.dart';


abstract class QuizzState{}

class QuizzStateImpl extends QuizzState{
    int currQ = 0;
    List<Question> quizz = [];
    int score = 0;

    QuizzStateImpl(this.currQ, this.quizz, this.score);
}

class QuizzCubit extends Cubit<QuizzState>{
    List<Question> _quizz = [];

    int _currQ = 0;
    int _score = 0;

    QuizzCubit(this._currQ, this._quizz, this._score) : super(QuizzStateImpl(0, [Question(questionText: "1 + 1 = 3", isCorrect: false, imageRef: "assets/math.jpg"),
        Question(questionText: "La France a gagné la coupe du monde de football 2022", isCorrect: false, imageRef: "assets/foot.jpg"),
        Question(questionText: "Eliud Kipchoge est le premier marathonien à passer sous la barre des 2h00 pour un marathon", isCorrect: true, imageRef: "assets/marathon.jpg")], 0));


    void nextQuestion(BuildContext context) {
      int size = _quizz.length;
      if(_currQ < size - 1){
        _currQ++;
      }
      else {
        Navigator.pushReplacement(context, MaterialPageRoute(builder: (context) => Results(_score, _quizz.length)));
      }
      emit(QuizzStateImpl(_currQ, _quizz, _score));
    }

    void checkAnswer(bool userChoice, BuildContext context){
      if(userChoice == _quizz.elementAt(_currQ).isCorrect) {
        _score ++;
      }
      emit(QuizzStateImpl(_currQ, _quizz, _score));
    }

    List<Question> get quizz => _quizz;
    set quizz(List<Question> questions){
      _quizz = questions;
      emit(QuizzStateImpl(currQ, quizz, _score));
    }

    int get currQ => _currQ;
    set currQ(int value) {
      _currQ = value;
      emit(QuizzStateImpl(currQ, quizz, _score));
    }



}