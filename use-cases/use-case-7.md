# USE CASE: 7 An Employee's Details can be Updated

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want *to update an Employee's details for a given employee* so that *I can ensure that Employee's details are kept up-to-date.*

### Scope

Company.

### Level

Primary task.

### Preconditions

We know the employee.  Database contains previous employee data.

### Success End Condition

The given Employee's details are updated.

### Failed End Condition

The given Employee's details are not updated.

### Primary Actor

HR Advisor.

### Trigger

An Employee notifies HR of updated details.

## MAIN SUCCESS SCENARIO

1. An Employee notifies HR of updated employee details.
2. HR advisor captures the employee to update, and the details to change.
3. HR advisor updates current information of the given employee.
4. HR advisor confirms employee details are updated to employee.

## EXTENSIONS

3. **Employee's updated details are Invalid/Incomplete**:
   1. HR advisor informs the Notifying Employee more details are needed from the Notifying Employee.


3. **Employee's new details are already captured**:
    1. HR advisor informs the Notifying Employee that the details are already correct.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0