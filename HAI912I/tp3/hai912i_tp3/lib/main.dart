import 'dart:ffi';

import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'package:hai912i_tp3/logic/quizzprovider.dart';
import 'package:hai912i_tp3/presentation/quizzPage.dart';
import 'package:hai912i_tp3/presentation/thematicSelectPage.dart';
import 'package:provider/provider.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
    const MyApp({Key? key}) : super(key: key);
    @override
    Widget build(BuildContext context) {

        return FutureBuilder (
            future: Firebase.initializeApp(),
            builder: (context, AsyncSnapshot<dynamic> snapshot) {
              return MaterialApp(

                home: ThematicSelect(),
              );
            }
        );
    }
}

