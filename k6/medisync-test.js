import http from 'k6/http';
import { check, sleep } from 'k6';
import encoding from 'k6/encoding';

export const options = {
  stages: [
    { duration: '10s', target: 5 },  // Sube a 5 usuarios virtuales
    { duration: '20s', target: 10 }, // Mantiene 10 usuarios concurrentes
    { duration: '10s', target: 0 },  // Baja a 0
  ],
  thresholds: {
    http_req_duration: ['p(95)<500'], // 95% de peticiones menores a 500ms
    http_req_failed: ['rate<0.01'],   // Menos del 1% de errores
  },
};

const BASE_URL = __ENV.BASE_URL || 'http://localhost:8081';
const authHeader = 'Basic ' + encoding.b64encode('medico:1234');

export default function () {
  const params = {
    headers: {
      Authorization: authHeader,
      'Content-Type': 'application/json',
    },
  };

  const resCitas = http.get(`${BASE_URL}/api/citas`, params);
  check(resCitas, { 'GET citas status 200': (r) => r.status === 200 });

  const resHistorial = http.get(`${BASE_URL}/api/consultas/paciente/paciente-test-01`, params);
  check(resHistorial, { 'GET historial status 200': (r) => r.status === 200 });

  sleep(1);
}