const mysql = require('mysql');

// Configura la conexión a la base de datos
const connection = mysql.createConnection({
  host: 'localhost',
  user: 'root',
  password: 'admin',
  database: 'bdfisiocir'
});

// Establece la conexión
connection.connect((error) => {
  if (error) {
    console.error('Error al conectar a la base de datos: ', error);
    return;
  }
  console.log('Conexión exitosa a la base de datos.');
});

// Realiza operaciones con la base de datos aquí...
connection.query('SELECT * FROM usuarios', (error, results, fields) => {
  if (error) {
    console.error('Error al ejecutar la consulta: ', error);
    return;
  }
  console.log('Resultados de la consulta: ', results);
});

// Cierra la conexión cuando hayas terminado
connection.end();
