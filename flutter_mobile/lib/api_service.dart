import 'package:http/http.dart' as http;

class ApiService {
  static const String baseUrl = 'http://ec2-13-48-27-139.eu-north-1.compute.amazonaws.com'; // Replace with your API URL

  static Future<http.Response> fetchCustomers() async {
    final response = await http.get(Uri.parse('$baseUrl/api/customers'));
    return response;
  }
}
