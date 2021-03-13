# USE CASE: 8 Delete an Employee's details

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want *to delete an employee's details* so that *the company is compliant with data retention legislation.*

### Scope

Company.

### Level

Primary task.

### Preconditions

We know the employee.  Database contains current employee data.

### Success End Condition

The Notifying Employee's details are deleted.

### Failed End Condition

No report is produced.

### Primary Actor

HR Advisor.

### Trigger

Under necessary data protection, data has to be deleted after the expiry of employment or at the request of the employee  

## MAIN SUCCESS SCENARIO

1. Data deletion is triggered either by expiry or request.
2. HR advisor captures the employee whose data has to be deleted.
3. HR advisor deletes the data of the given employee.

## EXTENSIONS

3. **Data Deletion is triggered by request**:
    1. HR advisor confirms to requesting employee that data has been deleted .

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0