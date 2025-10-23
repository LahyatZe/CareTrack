export interface CarePlanDto {
  id: string;
  patientId: string;
  title: string;
  description: string;
  goals: string[];
  startDate: string;
  endDate?: string;
  status: 'ACTIVE' | 'COMPLETED' | 'ON_HOLD';
}
