import 'package:http/http.dart' as http;

class ApiService {
  static const String baseUrl = 'http://localhost:8080'; // Replace with your API URL

  static Future<http.Response> fetchCustomers() async {
    final response = await http.get(Uri.parse('$baseUrl/api/customers'));
    return response;
  }
}
