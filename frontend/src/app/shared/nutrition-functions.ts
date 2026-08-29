

export function getMetabolismForMen(weight: number, height: number, age: number): number {
    return 10 * weight + 6.25 * height - 5 * age + 5
}

export function getMetabolismForWomen(weight: number, height: number, age: number): number {
    return 10 * weight + 6.25 * height - 5 * age + - 161
}

export function getCalories(metabolism: number, activityFactor: ActivityFactor) {
    return metabolism * activityFactor;
}

export function getMacros(calories: number): NutritionMacros {
    return {
        calories: Math.round(calories),
        proteins: Math.round((calories * 0.30) / 4),
        carbohydrates: Math.round((calories * 0.40) / 4),
        lipids: Math.round((calories * 0.30) / 9)
    };
}

export enum ActivityFactor {
    SEDENTARY = 1.2,
    LIGHTLY_ACTIVE = 1.375,
    MODERATELY_ACTIVE = 1.55,
    VERY_ACTIVE = 1.725,
    EXTREMELY_ACTIVE = 1.9
}

export interface NutritionMacros {
    calories: number;
    proteins: number;
    carbohydrates: number;
    lipids: number;
}