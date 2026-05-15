<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Student Management System</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      background: linear-gradient(to right, #eef2ff, #f8fafc);
      font-family: 'Segoe UI', sans-serif;
      min-height: 100vh;
    }

    .main-title {
      font-weight: 700;
      color: #1e293b;
    }

    .card {
      border: none;
      border-radius: 16px;
      box-shadow: 0 6px 18px rgba(0,0,0,0.08);
    }

    .btn-custom {
      background-color: #2563eb;
      color: white;
      border: none;
    }

    .btn-custom:hover {
      background-color: #1d4ed8;
    }

    .stats-card {
      text-align: center;
      padding: 20px;
    }

    .stats-number {
      font-size: 28px;
      font-weight: bold;
      color: #2563eb;
    }

    .table thead {
      background-color: #e2e8f0;
    }
  </style>
</head>
<body>

<div class="container py-5">

  <h1 class="text-center mb-5 main-title">🎓 Student Management System</h1>

  <div class="card p-4 mb-4">
    <h4 class="mb-4">➕ Add Student</h4>

    <form id="studentForm" class="row g-3">

      <div class="col-md-4">
        <label class="form-label">Student Name</label>
        <input type="text" id="name" class="form-control" required>
      </div>

      <div class="col-md-2">
        <label class="form-label">Age</label>
        <input type="number" id="age" class="form-control" required>
      </div>

      <div class="col-md-3">
        <label class="form-label">Department</label>
        <input type="text" id="department" class="form-control" required>
      </div>

      <div class="col-md-3">
        <label class="form-label">Marks</label>
        <input type="number" id="marks" class="form-control" required>
      </div>

      <div class="col-12">
        <button type="submit" class="btn btn-custom px-4">Add Student</button>
      </div>

    </form>
  </div>

  <div class="row mb-4">

    <div class="col-md-4">
      <div class="card stats-card">
        <h5>Total Students</h5>
        <div class="stats-number" id="totalStudents">0</div>
      </div>
    </div>

    <div class="col-md-4">
      <div class="card stats-card">
        <h5>Average Marks</h5>
        <div class="stats-number" id="averageMarks">0</div>
      </div>
    </div>

    <div class="col-md-4">
      <div class="card stats-card">
        <h5>Top Score</h5>
        <div class="stats-number" id="topScore">0</div>
      </div>
    </div>

  </div>

  <div class="card p-4">
    <h4 class="mb-4">📋 Student Records</h4>

    <div class="table-responsive">
      <table class="table table-bordered table-hover align-middle" id="studentTable">

        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Age</th>
            <th>Department</th>
            <th>Marks</th>
            <th>Grade</th>
            <th>Action</th>
          </tr>
        </thead>

        <tbody>

        </tbody>

      </table>
    </div>
  </div>

</div>

<script>

  let students = JSON.parse(localStorage.getItem('students')) || [];

  const form = document.getElementById('studentForm');
  const tableBody = document.querySelector('#studentTable tbody');

  form.addEventListener('submit', function(e) {
    e.preventDefault();

    const student = {
      id: Date.now(),
      name: document.getElementById('name').value,
      age: document.getElementById('age').value,
      department: document.getElementById('department').value,
      marks: parseFloat(document.getElementById('marks').value)
    };

    students.push(student);

    saveData();
    renderStudents();
    updateStatistics();

    form.reset();
  });

  function calculateGrade(marks) {
    if (marks >= 90) return 'A+';
    if (marks >= 80) return 'A';
    if (marks >= 70) return 'B';
    if (marks >= 60) return 'C';
    if (marks >= 50) return 'D';
    return 'Fail';
  }

  function renderStudents() {

    tableBody.innerHTML = '';

    students.forEach(student => {

      const row = document.createElement('tr');

      row.innerHTML = `
        <td>${student.id}</td>
        <td>${student.name}</td>
        <td>${student.age}</td>
        <td>${student.department}</td>
        <td>${student.marks}</td>
        <td>${calculateGrade(student.marks)}</td>
        <td>
          <button class="btn btn-danger btn-sm" onclick="deleteStudent(${student.id})">
            Delete
          </button>
        </td>
      `;

      tableBody.appendChild(row);
    });
  }

  function deleteStudent(id) {

    students = students.filter(student => student.id !== id);

    saveData();
    renderStudents();
    updateStatistics();
  }

  function updateStatistics() {

    document.getElementById('totalStudents').textContent = students.length;

    if (students.length === 0) {
      document.getElementById('averageMarks').textContent = '0';
      document.getElementById('topScore').textContent = '0';
      return;
    }

    const totalMarks = students.reduce((sum, student) => sum + student.marks, 0);

    const average = (totalMarks / students.length).toFixed(2);

    const topScore = Math.max(...students.map(student => student.marks));

    document.getElementById('averageMarks').textContent = average;
    document.getElementById('topScore').textContent = topScore;
  }

  function saveData() {
    localStorage.setItem('students', JSON.stringify(students));
  }

  renderStudents();
  updateStatistics();

</script>

</body>
</html>
