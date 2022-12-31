import 'package:flutter/material.dart';
import 'package:hai912i_tp3/data/databaseQuery.dart';

class AddingQuestion extends StatefulWidget{
  @override
  State<AddingQuestion> createState() => AddingQuestionState();
}

class AddingQuestionState extends State<AddingQuestion>{
  TextEditingController thematicFieldController = TextEditingController();
  TextEditingController questionFieldController = TextEditingController();
  bool? questionValue;
  QuizzQuery db = QuizzQuery().getInstance();

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.blueGrey,
        title: Center(child: Text("Ajouter une question")),),
      backgroundColor: Colors.blueGrey,
      body: Center(
        child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            children: [
              TextField(
                  controller: thematicFieldController,
                  decoration: InputDecoration(
                      enabledBorder: OutlineInputBorder(
                        borderSide: BorderSide(width: 3, color: Colors.grey),
                        borderRadius: BorderRadius.circular(20.0),
                      ),
                      labelText: "Thèmatique",
                      )
              ),

              TextField(
                  controller: questionFieldController,
                  decoration: InputDecoration(
                    enabledBorder: OutlineInputBorder(
                      borderSide: BorderSide(width: 3, color: Colors.grey),
                      borderRadius: BorderRadius.circular(20.0),
                    ),
                    labelText: "Question",
                  )
              ),

              Column(
                  children: [

                    RadioListTile(
                      title: Text("Vrai"),
                      value: true,
                      groupValue: questionValue,
                      onChanged: (value){
                        setState(() {
                          questionValue = value;
                        });
                      },
                    ),

                    RadioListTile(
                      title: Text("Faux"),
                      value: false,
                      groupValue: questionValue,
                      onChanged: (value){
                        setState(() {
                          questionValue = value;
                        });
                      },
                    ),

                  ],
              ),

              ElevatedButton(
                  onPressed: (){
                    setState(() {
                      try{
                        db.add(thematicFieldController.text, questionFieldController.text, questionValue);
                        ScaffoldMessenger.of(context).showSnackBar(SnackBar(
                          content: Text("Question ajouté !"),
                        ));

                      }on Exception catch(e){
                        ScaffoldMessenger.of(context).showSnackBar(SnackBar(
                          content: Text("ERREUR : question non ajouté !"),
                        ));
                      }
                      questionFieldController.clear();
                      thematicFieldController.clear();


                    });
                  },
                  child: Text("Ajouter"))
            ]
        ),
      ),
    );
  }

}