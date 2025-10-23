export interface AlertDto {
  id: string;
  patientId: string;
  type: 'MEDICATION' | 'VITAL' | 'APPOINTMENT' | 'PLAN';
  severity: 'LOW' | 'MEDIUM' | 'HIGH';
  message: string;
  createdAt: string;
  acknowledged: boolean;
}
