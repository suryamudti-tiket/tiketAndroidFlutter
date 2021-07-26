import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'Flutter Demo Home Test'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);
  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {

  List<String> account = [];
  static const platform = const MethodChannel('tiket/registration_channel');

  @override
  initState() {
    _setupPlatformChannel();
    super.initState();
  }

  Future<void> _setupPlatformChannel() async {
    try {
      platform.setMethodCallHandler((call) {
        if (call.method == "login_user") {
          if (call.arguments != null) {
            setState(() {
              account.add(call.arguments.toString());
            });

          }
        }
        return;
      });

    } on PlatformException catch (e) {
      print(e);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
          child: ListView.builder(
              itemCount: account.length,
              itemBuilder: (ctx, index) {
                return ListTile(
                  title: Text(account[index]),
                  onTap: () async {
                    await platform.invokeMethod("login_user", account[index]);
                  },
                );
              }
          )
      ),
    );
  }
}
