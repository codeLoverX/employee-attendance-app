import { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import { toast } from 'react-toastify';
import { axiosFetch } from "../../api/fetch";
export const AddEditEmployeeForm = ({
    currentEmployee,
    mode,
    handleCurrentEmployeeIndex,
    addEmployee,
    editEmployee
}) => {
    const [loading, setLoading] = useState(false);
    const { register, handleSubmit, reset } = useForm();
    const onSubmit = async (data, event) => {
        setLoading(true);
        event.preventDefault();
        if (mode === "ADD") {
            try {
                const response = await axiosFetch.post('/employee', data)
                await setTimeout(() => {
                    setLoading(false);
                    toast.success("Successfully added the employee", {
                        position: toast.POSITION.TOP_RIGHT
                    });
                    console.log({ response })
                    addEmployee({ ...response.data });
                    reset();
                }, 3000);
            }
            catch (error) {
                setLoading(false);
                toast.error(`${error.response?.status || ""} Error: ${error.response?.error || error.message}`)
            }
        }
        else {
            try {
                const response = await axiosFetch.put(`/employee/${currentEmployee.id}`, {
                    ...data,
                })

                await setTimeout(() => {
                    setLoading(false);
                    toast.success("Successfully edited the employee", {
                        position: toast.POSITION.TOP_RIGHT
                    });
                    console.log({ response })
                    editEmployee({ ...currentEmployee, ...response.data });
                    reset();
                }, 3000);
            } catch (error) {
                setLoading(false);
                toast.error(`${error.response?.status || ""} Error: ${error.response?.error || error.message}`)
            }
        }
    }
    useEffect(() => {
        let defaults = {
            name: currentEmployee?.name,
            designation: currentEmployee?.designation,
            department: currentEmployee?.department
        }
        reset(defaults);
    }, [currentEmployee, reset, mode])
    return (
        <div>
            <h1 className='pt-4 pb-5 text-xl font-bold'>
                {mode === "ADD" ? <> Add Employees... </> : <> Edit Employees... </>}
            </h1>

            <form
                className="text-base w-3/4"
                onSubmit={handleSubmit(onSubmit)}
            >
                <div className="mx-auto">
                    <input
                        id="name"
                        type="text"
                        defaultValue={currentEmployee.name}
                        {...register("name")}
                        required={true}
                        placeholder="Enter your name..."
                        className="input input-md input-bordered dark:bg-white inline w-full mb-2"
                    />
                    <div className="grid lg:grid-cols-2">
                        <input
                            id="designation"
                            defaultValue={currentEmployee.designation}
                            {...register("designation")}
                            required={true}
                            title="Enter your desc please"
                            placeholder="Enter your designation please..."
                            className="input input-md input-bordered dark:bg-white inline w-full mb-2"
                        />
                        <input id="department"
                            defaultValue={currentEmployee.department}
                            {...register("department")}
                            required={true}
                            title="Enter your department please"
                            placeholder="Enter your department please..."
                            className="input input-md input-bordered dark:bg-white inline w-full mb-2"
                        />
                    </div>
                    <div className="flex justify-left">
                        {
                            mode === "ADD" ?
                                <>
                                    <button className={`btn btn-sm btn-primary mt-4 ${loading ? "loading" : ""}`} type="submit" >
                                        Add Employee
                                    </button>
                                </>
                                :
                                <>
                                    <button className={`btn btn-sm btn-primary mt-4 ${loading ? "loading" : ""}`} type="submit">
                                        Edit Employee

                                    </button>
                                    <button className={`btn btn-sm btn-primary mt-4 ml-5`}
                                        onClick={() => { reset(); handleCurrentEmployeeIndex(-1); }}>
                                        Add mode
                                    </button>
                                </>
                        }
                    </div>

                </div>
            </form>
        </div>
    );
};

