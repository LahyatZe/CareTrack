package com.caretrack.core.security;

/**
 * Represents the roles supported by the application. The values are stored as strings
 * in the persistence layer and translated into Spring Security authorities.
 */
public enum UserRole {
    ADMIN,
    USER
}
