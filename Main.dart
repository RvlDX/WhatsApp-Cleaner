import 'package:flutter/material.dart';

void main() {
  runApp(FileCleanerApp());
}

class FileCleanerApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'File Cleaner',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: CleanOptionsScreen(),
    );
  }
}

class CleanOptionsScreen extends StatefulWidget {
  @override
  _CleanOptionsScreenState createState() => _CleanOptionsScreenState();
}

class _CleanOptionsScreenState extends State<CleanOptionsScreen> {
  bool isSharedChecked = false;
  bool isDatabasesChecked = false;
  bool isBackupsChecked = false;
  bool isStatusesChecked = false;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('File Cleaner'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            // Checkbox for .shared folder
            CheckboxListTile(
              title: Text('.shared'),
              value: isSharedChecked,
              onChanged: (bool? value) {
                setState(() {
                  isSharedChecked = value ?? false;
                });
              },
            ),

            // Checkbox for Databases folder
            CheckboxListTile(
              title: Text('Databases'),
              value: isDatabasesChecked,
              onChanged: (bool? value) {
                setState(() {
                  isDatabasesChecked = value ?? false;
                });
              },
            ),

            // Checkbox for Backups folder
            CheckboxListTile(
              title: Text('Backups'),
              value: isBackupsChecked,
              onChanged: (bool? value) {
                setState(() {
                  isBackupsChecked = value ?? false;
                });
              },
            ),

            // Checkbox for .statuses folder
            CheckboxListTile(
              title: Text('.statuses'),
              value: isStatusesChecked,
              onChanged: (bool? value) {
                setState(() {
                  isStatusesChecked = value ?? false;
                });
              },
            ),

            SizedBox(height: 20),

            // Button to clean all
            ElevatedButton(
              onPressed: () {
                cleanAllFiles();
              },
              child: Text('Bersihkan Semua'),
            ),

            SizedBox(height: 20),

            // Button to clean selected
            ElevatedButton(
              onPressed: () {
                cleanSelectedFiles();
              },
              child: Text('Bersihkan yang Dipilih'),
            ),
          ],
        ),
      ),
    );
  }

  void cleanAllFiles() {
    // Logic for cleaning all files (implement your logic here)
    print('Semua file berhasil dibersihkan!');
  }

  void cleanSelectedFiles() {
    // Logic for cleaning selected folders (implement your logic here)
    if (isSharedChecked) print('.shared folder dibersihkan');
    if (isDatabasesChecked) print('Databases folder dibersihkan');
    if (isBackupsChecked) print('Backups folder dibersihkan');
    if (isStatusesChecked) print('.statuses folder dibersihkan');
  }
}
