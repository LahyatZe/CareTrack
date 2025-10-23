export interface VitalSignDto {
  id: string;
  patientId: string;
  type: 'HEART_RATE' | 'BLOOD_PRESSURE' | 'TEMPERATURE' | 'RESPIRATION' | 'OXYGEN_SATURATION';
  value: string;
  recordedAt: string;
  recordedBy: string;
}
