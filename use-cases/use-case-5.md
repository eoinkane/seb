# USE CASE: 5 Add a New Employee's details

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want *to add a New Employee's details * so that *I can ensure the New Employee is paid.*

### Scope

Company.

### Level

Primary task.

### Preconditions

We know the New Employee's details.

### Success End Condition

The New Employee can be paid as their details are in the system. 

### Failed End Condition

The New Employee cannot be paid.

### Primary Actor

HR Advisor.

### Trigger

A request to hire a new employee is sent to HR.

## MAIN SUCCESS SCENARIO

1. A department requests to hire a new employee.
2. HR advisor captures the New Employee's details.
3. HR advisor inserts New Employee's details.

## EXTENSIONS

3. **New Employee's details are Invalid/Incomplete**:
    1. HR advisor informs the department more details are needed from the New Employee.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0