import 'dart:math';
import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Calculadora',
      theme: ThemeData(
        primarySwatch: Colors.indigo,
      ),
      home: const CalculadoraScreen(),
    );
  }
}

class CalculadoraScreen extends StatefulWidget {
  const CalculadoraScreen({super.key});

  @override
  State<CalculadoraScreen> createState() =>
      _CalculadoraScreenState();
}

class _CalculadoraScreenState
    extends State<CalculadoraScreen> {

  final txtNum1 = TextEditingController();
  final txtNum2 = TextEditingController();

  String resultado = "0";

  double get n1 =>
      double.tryParse(txtNum1.text) ?? 0;

  double get n2 =>
      double.tryParse(txtNum2.text) ?? 0;

  void calcular(String operacion) {

    double res = 0;

    switch (operacion) {

      case "+":
        res = n1 + n2;
        break;

      case "-":
        res = n1 - n2;
        break;

      case "*":
        res = n1 * n2;
        break;

      case "/":
        if (n2 == 0) {
          setState(() {
            resultado = "Error";
          });
          return;
        }
        res = n1 / n2;
        break;

      case "^":
        res = pow(n1, n2).toDouble();
        break;

      case "√":
        res = sqrt(n1);
        break;
    }

    setState(() {
      resultado = res.toStringAsFixed(2);
    });
  }

  Widget boton(String texto) {
    return SizedBox(
      width: 90,
      height: 55,
      child: ElevatedButton(
        onPressed: () => calcular(texto),
        child: Text(
          texto,
          style: const TextStyle(fontSize: 22),
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(
        title: const Text("Calculadora"),
        centerTitle: true,
      ),

      body: Padding(
        padding: const EdgeInsets.all(20),

        child: Card(
          elevation: 8,

          child: Padding(
            padding: const EdgeInsets.all(20),

            child: Column(
              children: [

                TextField(
                  controller: txtNum1,
                  keyboardType: TextInputType.number,
                  decoration: const InputDecoration(
                    labelText: "Número 1",
                  ),
                ),

                const SizedBox(height: 15),

                TextField(
                  controller: txtNum2,
                  keyboardType: TextInputType.number,
                  decoration: const InputDecoration(
                    labelText: "Número 2",
                  ),
                ),

                const SizedBox(height: 25),

                Row(
                  mainAxisAlignment:
                  MainAxisAlignment.spaceEvenly,
                  children: [
                    boton("+"),
                    boton("-"),
                    boton("*"),
                  ],
                ),

                const SizedBox(height: 10),

                Row(
                  mainAxisAlignment:
                  MainAxisAlignment.spaceEvenly,
                  children: [
                    boton("/"),
                    boton("^"),
                    boton("√"),
                  ],
                ),

                const SizedBox(height: 30),

                const Text(
                  "Resultado",
                  style: TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.bold,
                  ),
                ),

                const SizedBox(height: 10),

                Container(
                  width: double.infinity,
                  padding: const EdgeInsets.all(15),
                  decoration: BoxDecoration(
                    color: Colors.indigo.shade50,
                    borderRadius:
                    BorderRadius.circular(10),
                  ),
                  child: Text(
                    resultado,
                    textAlign: TextAlign.center,
                    style: const TextStyle(
                      fontSize: 28,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}