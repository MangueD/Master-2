import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/material.dart';

import '../logic/question.dart';


class QuizzQuery{
  CollectionReference db = FirebaseFirestore.instance.collection('quizz');
  List<String> thematics = [];
  List<Question> quizz = [];
  static QuizzQuery instance = QuizzQuery();

  QuizzQuery getInstance(){
    return instance;
  }

  void add(String thematic, String question, bool? value) {
    if(question == "" || value == null){
      print("question: $question");
      throw Exception();
    }

    queryThematics();

    print(thematic);
    if(!thematics.contains(thematic)){
      print("ok");
      thematic = "divers";
    }

    CollectionReference reference = db.doc(thematic).collection("questions");

    reference.add({"text": question, "response": value});

  }

  Future<void> queryThematics() async {
    QuerySnapshot querySnapshot = await db.get();
    List<QueryDocumentSnapshot> queryDocumentSnapshot = querySnapshot.docs;

    List<String> thematics = [];
    for (var doc in queryDocumentSnapshot) {
      if (doc.data() != null) {
        thematics.add(doc.id);
      }
    }
    this.thematics = thematics;
  }

  Future<void> queryQuestions(String thematic) async{
    DocumentSnapshot themaSnap = await db.doc(thematic).get();

    String imageUrl = "";
    if(themaSnap != null){
      Map<String, dynamic> themaSnapData = themaSnap.data() as Map<String, dynamic>;
      imageUrl = themaSnapData["imageUrl"];
    }

    QuerySnapshot querySnapshot = await db.doc(thematic).collection("questions").get();
    List<QueryDocumentSnapshot> queryDocumentSnapshot = querySnapshot.docs;

    List<Question>quizz = [];
    for (var doc in queryDocumentSnapshot) {
      if (doc.data() != null) {
        var data = doc.data() as Map<String, dynamic>;
        quizz.add(Question(questionText: data["text"], isCorrect: data["response"], imageRef: imageUrl));
      }
    }

    this.quizz = quizz;
  }
}